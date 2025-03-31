using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using RideShareServer;

public class ClientHandler
{
    private readonly TcpClient _client;

    public ClientHandler(TcpClient client)
    {
        _client = client;
    }

    public void HandleClient()
    {
        using (var stream = _client.GetStream())
        using (var reader = new StreamReader(stream))
        using (var writer = new StreamWriter(stream) { AutoFlush = true })
        {
            string message;
            while ((message = reader.ReadLine()) != null)
            {
                string response = ProcessMessage(message);
                writer.WriteLine(response);
            }
        }
    }

    private string ProcessMessage(string message)
    {
        try
        {
            if (message.StartsWith("login-"))
            {
                return HandleLogin(message.Substring(6));
            }
            else if (message.StartsWith("signup-"))
            {
                return HandleSignup(message.Substring(7));
            }
            else if (message.StartsWith("add-commute-"))
            {
                return HandleAddCommute(message.Substring(12));
            }
            else if (message.StartsWith("get-commutes-"))
            {
                return HandleGetCommutes(message.Substring(13));
            }
            return "error|Unknown command\n";
        }
        catch (Exception ex)
        {
            return $"error|{ex.Message}\n";
        }
    }

    private string HandleLogin(string loginData)
    {
        string[] parts = loginData.Split('|');

        if (parts.Length != 3)
            return "login-err|Invalid login format\n";  

        string lastName = parts[0];
        string firstName = parts[1];
        string password = parts[2];

        if (UserStore.Validate(lastName, firstName, password))
        {
            return $"login-ok|{lastName}|{firstName}|{password}\n";
        }

        return "login-err|Invalid login format\n";  
    }
    private string HandleSignup(string signupData)
    {
        string[] parts = signupData.Split('|');
        if (parts.Length != 4)
        {
            return "signup-err|Invalid format. Expected: firstName|lastName|city|password\n";
        }

        string firstName = parts[0];
        string lastName = parts[1];
        string city = parts[2];
        string password = parts[3];

        string key = $"{lastName.ToLower()}{firstName.ToLower()}";
        if (UserStore.GetUser(key) != null)
        {
            return "signup-err|User already exists\n";
        }

        User newUser = new User(firstName, lastName, city, password);
        UserStore.AddUser(key, newUser);

        return "signup-ok\n";
    }


    private string HandleAddCommute(string commuteData)
    {
        string[] parts = commuteData.Split('|');
        if (parts.Length != 6)
            return "add-commute-err|Invalid format. Expected: date|time|departure|destination|maxSeats|occupiedSeats\n";

        string date = parts[0];
        string time = parts[1];
        string departure = parts[2];
        string destination = parts[3];

        if (!int.TryParse(parts[4], out int maxSeats) || !int.TryParse(parts[5], out int occupiedSeats))
            return "add-commute-err|Invalid seat numbers\n";

        string firstName = "Florentin";  
        string lastName = "Moldovan";    

        // Create a new commute
        Commute commute = new Commute(date, time, departure, destination, maxSeats, occupiedSeats);

        return UserStore.AddCommute(firstName, lastName, commute);
    }


    private string HandleGetCommutes(string userData)
    {
        string[] parts = userData.Split('|');

        if (parts.Length != 2)
        {
            return "get-commutes-err|Invalid format. Expected: firstName|lastName\n";
        }

        string firstName = parts[0];
        string lastName = parts[1];

        string key = $"{lastName.ToLower()}{firstName.ToLower()}";
        User user = UserStore.GetUser(key);

        if (user == null)
        {
            return "get-commutes-err|User not found\n";
        }

        List<Commute> commutes = user.GetCommutesAsList();

        if (commutes == null || commutes.Count == 0)
        {
            return "get-commutes-err|No commutes found\n";
        }

        if (!user.HasCommuteIndex())
        {
            user.SetCommuteIndex(0); 
        }

        int currentIndex = user.GetCommuteIndex();

        if (currentIndex < commutes.Count)
        {
            Commute nextCommute = commutes[currentIndex];

            string commuteData = $"{nextCommute.Date}|{nextCommute.Time}|{nextCommute.Departure}|{nextCommute.Destination}|{nextCommute.MaxSeats}|{nextCommute.OccupiedSeats}";

            user.SetCommuteIndex(currentIndex + 1);

            return $"get-commute-ok|{commuteData}\n";
        }
        else
        {
            return "get-commutes-err|No more commutes\n";
        }
    }
}
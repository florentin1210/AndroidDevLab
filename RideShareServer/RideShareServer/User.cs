using System.Collections.Generic;

namespace RideShareServer
{
    public class User
    {
        public string LastName { get; }
        public string FirstName { get; }
        public string Location { get; }
        public string Password { get; }

        private readonly List<Commute> _commutes;
        private int commuteIndex = 0; 
        public User(string lastName, string firstName, string location, string password)
        {
            LastName = lastName;
            FirstName = firstName;
            Location = location;
            Password = password;
            _commutes = new List<Commute>();
        }

        public void AddCommute(Commute commute)
        {
            _commutes.Add(commute);
        }

        public List<string> GetCommutes()
        {
            var commuteDescriptions = new List<string>();
            foreach (var commute in _commutes)
            {
                // Format: date|time|departure|destination|maxSeats|occupiedSeats
                commuteDescriptions.Add($"{commute.Date}|{commute.Time}|{commute.Departure}|{commute.Destination}|{commute.MaxSeats}|{commute.OccupiedSeats}");
            }
            return commuteDescriptions;
        }
        public List<Commute> GetCommutesAsList()
        {
            return new List<Commute>(_commutes);
        }
        public bool HasCommuteIndex()
        {
            return commuteIndex >= 0;
        }

        public int GetCommuteIndex()
        {
            return commuteIndex;
        }

        public void SetCommuteIndex(int index)
        {
            commuteIndex = index;
        }

    }
}

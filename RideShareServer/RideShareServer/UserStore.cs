using System.Collections.Concurrent;

namespace RideShareServer
{
    public static class UserStore
    {
        private static readonly ConcurrentDictionary<string, User> _users =
            new ConcurrentDictionary<string, User>();

        static UserStore()
        {
            // Add test user Florentin Moldovan
            var user = new User("Moldovan", "Florentin", "Poienita", "test123");

            // Add two commutes to this user
            user.AddCommute(new Commute("2025-03-25", "08:00", "Poienita", "Targu-Mures", 4, 0));
            user.AddCommute(new Commute("2025-03-26", "09:00", "Poienita", "Targu-Mures", 4, 0));

            _users.TryAdd("moldovanflorentin", user);
        }

        public static bool AddUser(string email, User user)
        {
            return _users.TryAdd(email.ToLower(), user);
        }

        public static User GetUser(string email)
        {
            _users.TryGetValue(email.ToLower(), out User user);
            return user;
        }

        public static bool Validate(string lastName, string firstName, string password)
        {
            string key = $"{lastName.ToLower()}{firstName.ToLower()}";
            return _users.TryGetValue(key, out User user) && user.Password == password;
        }

        public static string AddCommute(string firstName, string lastName, Commute commute)
        {
            string key = $"{lastName.ToLower()}{firstName.ToLower()}"; 
            if (_users.TryGetValue(key, out User user))
            {
                user.AddCommute(commute);
                return "add-commute-ok|Commute added successfully\n";
            }
            return "add-commute-err|User not found\n";
        }
    }
}

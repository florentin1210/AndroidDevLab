namespace RideShareServer
{
    public class Commute
    {
        public string Date { get; set; }
        public string Time { get; set; }
        public string Departure { get; set; }
        public string Destination { get; set; }
        public int MaxSeats { get; set; }
        public int OccupiedSeats { get; set; }

        public Commute(string date, string time, string departure, string destination, int maxSeats, int occupiedSeats)
        {
            Date = date;
            Time = time;
            Departure = departure;
            Destination = destination;
            MaxSeats = maxSeats;
            OccupiedSeats = occupiedSeats;
        }
    }
}

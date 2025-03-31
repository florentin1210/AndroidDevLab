using System;
using System.Net;
using System.Net.Sockets;

namespace RideShareServer
{
    class Program
    {
        static void Main()
        {
            var server = new ServerCore();
            server.Start(8000);

            Console.WriteLine("Server running. Press Q to quit");
            while (Console.ReadKey().Key != ConsoleKey.Q) { }

            server.Stop();
        }
    }
}
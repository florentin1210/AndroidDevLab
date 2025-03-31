using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace RideShareServer
{
    public class ServerCore
    {
        private TcpListener _listener;
        private bool _isRunning;

        public void Start(int port)
        {
            _listener = new TcpListener(IPAddress.Any, port);
            _listener.Start();
            _isRunning = true;

            Console.WriteLine($"Server started on port {port}");

            new Thread(() =>
            {
                while (_isRunning)
                {
                    try
                    {
                        TcpClient client = _listener.AcceptTcpClient();
                        Console.WriteLine("New client connected");
                        new ClientHandler(client).HandleClient();
                    }
                    catch (Exception ex)
                    {
                        if (_isRunning)
                            Console.WriteLine($"Accept error: {ex.Message}");
                    }
                }
            }).Start();
        }

        public void Stop()
        {
            _isRunning = false;
            _listener?.Stop();
            Console.WriteLine("Server stopped");
        }
    }
}
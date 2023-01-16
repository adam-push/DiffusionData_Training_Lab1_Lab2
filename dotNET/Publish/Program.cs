using System;
using System.Threading;
using System.Threading.Tasks;
using PushTechnology.ClientInterface.Client.Factories;
using PushTechnology.ClientInterface.Client.Features;
using PushTechnology.ClientInterface.Client.Topics;

namespace PubSub
{
    class Program
    {
        static string Host = "localhost";
        static int Port = 8080;
        static string Principal = "admin";
        static string Credential = "password";

        static async Task Main(string[] args)
        {
            // Add a topic and set its value.
            var session = Diffusion.Sessions
                .Principal(Principal)
                .Password(Credential)
                .Open("ws://"+Host+":"+Port.ToString());

            await session.TopicControl.AddTopicAsync( "my/first/topic", TopicType.STRING);

            int i = 0;
            while (true)
            {
                var stringDataType = Diffusion.DataTypes.String;
                var msg = "Hello World - " + i.ToString();
                Console.WriteLine(msg);
                var result = session.TopicUpdate.SetAsync(
                    "my/first/topic",
                    msg);
                i++;
                Thread.Sleep(1000);
            }
        }
    }
}

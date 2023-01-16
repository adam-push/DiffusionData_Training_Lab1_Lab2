using System;
using System.Threading;
using System.Threading.Tasks;
using PushTechnology.ClientInterface.Client.Callbacks;
using PushTechnology.ClientInterface.Client.Factories;
using PushTechnology.ClientInterface.Client.Features;
using PushTechnology.ClientInterface.Client.Features.Topics;
using PushTechnology.ClientInterface.Client.Topics.Details;
using PushTechnology.ClientInterface.Data.JSON;

namespace Subscribe
{
    class Program
    {
        static string Host = "localhost";
        static int Port = 8080;
        static string Principal = "admin";
        static string Credential = "password";

        static async Task Main(string[] args)
        {
            //Subscribe to topics.
            var session = Diffusion.Sessions
                .Principal(Principal)
                .Password(Credential)
                .Open("ws://" + Host + ":" + Port.ToString());

            session.Topics.AddStream("my/first/topic", new ExampleValueStream());
            session.Topics.SubscribeAsync("my/first/topic").Wait();
            while (true)
            {
                Thread.Sleep(100);
            }
        }
    }

    class ExampleValueStream : IValueStream<string>
    {
        public void OnValue(string topicPath, ITopicSpecification specification,
            string oldValue, string newValue)
        {
            Console.WriteLine(newValue);
        }

        public void OnError(ErrorReason errorReason)
        {
            if (!ErrorReason.SESSION_CLOSED.Equals(errorReason))
            {
                Console.WriteLine($"OnError {errorReason}");
            }
        }

        public void OnSubscription(string topicPath, ITopicSpecification specification)
        {
            Console.WriteLine($"Subscribed to: {topicPath}");
        }

        public void OnUnsubscription(string topicPath, ITopicSpecification specification, TopicUnsubscribeReason reason)
        {
            Console.WriteLine($"Unsubscribed from: {topicPath}");
        }

        public void OnValue(string topicPath, ITopicSpecification specification, IJSON oldValue, IJSON newValue)
        {
            Console.WriteLine($"{topicPath}: {newValue.ToJSONString()}");
        }

        public void OnClose()
        {
            // Not used
        }
    }
}


console.log("Diffusion Data Labs, loading diffusion library");
const diffusion = require("diffusion");
const TopicType = diffusion.topics.TopicType;

const host = "localhost";
const port = 8080;
const principal = "admin";
const password = "password";

// Add a topic and set its value
console.log("Connecting...");
diffusion
  .connect({
    host: host,
    port: port,
    secure: false,
    principal: principal,
    credentials: password,
  })
  .then(function (session) {
    console.log("Connected");

    // Create topic
    var TopicSpecification = diffusion.topics.TopicSpecification;
    session.topics.add(
      "my/first/topic",
      new TopicSpecification(TopicType.STRING)
    );

    // Send updates
    let i = 0;
    setInterval(publish, 1000);

    function publish() {
      i += 1;
      session.topicUpdate.set(
        "my/first/topic",
        diffusion.datatypes.string(),
        "Hello World -" + i
      );
      console.log("Count:", i);
    }
  })
  .catch((error) => console.log(error));

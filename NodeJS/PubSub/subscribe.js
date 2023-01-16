console.log("Diffusion Data Labs, loading diffusion library");
const diffusion = require("diffusion");

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

    session
      .addStream("my/first/topic", diffusion.datatypes.string())
      .on("value", function (topic, spec, newValue, oldValue) {
        console.log(topic + ":", newValue);
      });
    session.select("my/first/topic");
  })
  .catch((error) => console.log(error));

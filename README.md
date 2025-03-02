
# SocketStream

SocketStream is a simple, real-time chat application built with Spring Boot and WebSockets (with SockJS fallback). It demonstrates full-duplex communication using the STOMP protocol over WebSockets, making it ideal for interviews, portfolio showcases, or as a starting point for more complex real-time projects.

## Features

- **Real-Time Messaging:** Instant exchange of messages between connected clients.
- **STOMP over WebSocket:** Uses the STOMP protocol for structured messaging.
- **SockJS Fallback:** Ensures compatibility with browsers that do not support native WebSockets.
- **Minimal UI:** A clean HTML interface to send and receive messages.

## Architecture Overview

SocketStream is composed of two main components:

1. **Backend:**
   - **WebSocket Configuration:** Sets up the `/ws` endpoint and configures an in-memory message broker.
   - **Chat Controller:** Listens for messages on `/app/chat` and broadcasts them to `/topic/messages`.

2. **Frontend:**
   - A simple HTML page that leverages SockJS and the STOMP JavaScript libraries to connect to the backend, subscribe to message topics, and send messages.

## Technologies Used

- **Spring Boot:** For building the backend application.
- **WebSockets & STOMP:** For enabling real-time, bidirectional communication.
- **SockJS:** Provides fallback transports for browsers without native WebSocket support.
- **HTML/CSS/JavaScript:** For creating the user interface.
- **Maven:** For dependency management and building the project.

## Getting Started

### Prerequisites

- **Java 8+**
- **Maven**

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/SachinPulse-bit/SocketStream.git
   cd socketstream
   ```

2. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

4. **Access the Chat Application:**

   Open your browser and navigate to:
   ```
   http://localhost:8080/chat.html
   ```

## Project Structure

```
socketstream/
├── src/main/java/com/example/socketstream
│   ├── config/
│   │   └── WebSocketConfig.java
│   ├── controller/
│   │   └── ChatController.java
│   └── SocketStreamApplication.java
├── src/main/resources
│   ├── static/
│   │   └── chat.html
│   └── application.properties
├── pom.xml
└── README.md
```

## How It Works

- **Connection Setup:**  
  When the client loads the chat page, it initiates a SockJS connection to `/ws`. SockJS first sends an HTTP GET to `/ws/info` to obtain server configuration details such as supported transports and cookie requirements.

- **WebSocket Handshake:**  
  If native WebSocket support is available, the connection is upgraded using the standard HTTP handshake (with a `101 Switching Protocols` response). Otherwise, SockJS falls back to alternative transports like XHR or long-polling.

- **Message Flow:**  
  - **Client:** Sends messages to the `/app/chat` endpoint.
  - **Server:** The `ChatController` receives these messages, processes them, and broadcasts them to all subscribers on `/topic/messages` using the in-memory message broker.
  - **Client:** Receives the broadcast messages in real time and updates the chat interface.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests for improvements, bug fixes, or new features.



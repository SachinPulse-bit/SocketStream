
## **WebSockets & SockJS Overview**

- **WebSockets:**
  - Enable full-duplex (two-way) communication over a single TCP connection.
  - Ideal for real-time applications (chat apps, live notifications, dashboards).

- **SockJS:**
  - A JavaScript library that provides a WebSocket-like object.
  - Offers fallback options (XHR, long-polling, etc.) when native WebSockets aren’t supported.
  - Enhances cross-browser compatibility and reliability.

---

## **Spring Boot WebSocket Setup**

### **1. Configuration with `WebSocketConfig`**
- **Endpoint Registration:**
  - Registers `/ws` endpoint using:
    ```java
    registry.addEndpoint("/ws").withSockJS();
    ```
  - Exposes the WebSocket endpoint with SockJS fallback.
  
- **Message Broker Setup:**
  - **Simple Broker:**  
    - Configured for destinations prefixed with `/topic`.
    - Broadcasts messages to all subscribers.
  - **Application Destination Prefix:**
    - Uses `/app` prefix for messages directed to methods annotated with `@MessageMapping`.

### **2. Controller Handling (`ChatController`)**
- **Message Mapping:**
  - Listens on `/app/chat` using `@MessageMapping("/chat")`.
- **Broadcasting:**
  - Sends processed messages to `/topic/messages` using `@SendTo("/topic/messages")`.
- **Example Code:**
  ```java
  @Controller
  public class ChatController {
      @MessageMapping("/chat")
      @SendTo("/topic/messages")
      public String send(String message) throws Exception {
          Thread.sleep(1000);  // Simulate processing delay
          return message;
      }
  }
  ```

---

## **Connection Flow & HTTP Handshake**

### **Initial Connection Process**
1. **HTTP Info Request:**
   - Client (via SockJS) sends an HTTP GET request to:
     ```
     http://localhost:8080/ws/info?t=<timestamp>
     ```
   - **Response Example:**
     ```json
     {
       "entropy": -1489988631,
       "origins": ["*:*"],
       "cookie_needed": true,
       "websocket": true
     }
     ```
   - **Purpose:**
     - Informs the client about server capabilities (WebSocket support, cookie requirements, allowed origins).
     - Helps SockJS decide on the best transport method.

2. **Transport Selection & HTTP Handshake:**
   - **If WebSocket is Supported:**
     - SockJS upgrades the connection by sending an HTTP GET with `Upgrade: websocket`.
     - Server replies with `101 Switching Protocols`, confirming the switch.
     - The connection URL may look like:
       ```
       ws://localhost:8080/ws/829/trqnck01/websocket
       ```
   - **If Not Supported:**
     - SockJS automatically falls back to alternative transports (XHR, long-polling, etc.).


---

## **Message Flow**

1. **Client-Side:**
   - Establishes a SockJS connection.
   - Uses STOMP over the connection.
   - Subscribes to `/topic/messages` to receive broadcasts.
   - Sends messages to `/app/chat`.

2. **Server-Side:**
   - `ChatController` receives messages at `/app/chat`.
   - Processes and broadcasts them to `/topic/messages` via the message broker.
   - All connected clients receive the messages in real time.

---

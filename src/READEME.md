# YapChat – Real-Time Voice Chat Application

## 📌 Project Overview
**YapChat** is a real-time chat application that enables **text and voice communication** between multiple users over a TCP/IP network, using a client-server architecture. Developed in Java, it provides a simple and intuitive interface for seamless communication.

---

## ✨ Features
- **Real-time text messaging** between connected users
- **Voice message support** with recording and playback
- **Multi-user communication** via a central server
- **Simple and clean GUI** built with Java Swing
- **Threaded client handling** for concurrent connections

---

## 🏗️ System Architecture
The project follows a **client-server model**:

### **Server (`Serveur.java`)**
- Listens for incoming connections on port `5000`
- Manages multiple clients simultaneously
- Broadcasts text and voice messages to all connected clients
- Handles client disconnections gracefully

### **Client (`Client.java`)**
- Connects to the server
- Sends and receives text/voice messages
- Manages network input/output streams

### **Network Controller (`NetworkController.java`)**
- Acts as an intermediary between the GUI and network layer
- Coordinates message sending/receiving
- Communicates with the audio module

### **GUI (`YapChatGUI.java`)**
- User-friendly interface for text input and display
- Voice recording and playback controls
- Connection management

---

## 🔧 Technologies Used
- **Programming Language:** Java (JDK 8+)
- **Network Protocol:** TCP/IP
- **GUI Framework:** Java Swing
- **Network Communication:** Java Sockets
- **Audio Handling:** Java Sound API

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- Microphone and speakers for voice functionality

### Running the Application

1. **Start the Server:**
   ```bash
   java Serveur
   ```

2. **Launch Clients:**
   ```bash
   java Main
   ```
    - Enter a username when prompted
    - Connect to the server using the GUI

---

## 📡 Communication Protocol
The system supports two types of messages:

### **Text Messages**
- Plain strings sent from a client to the server
- Broadcasted to all connected clients

### **Voice Messages**
- Audio captured from the microphone as byte arrays
- Transmitted and played back on receiving clients

Each message includes the sender’s name and content (text or audio data).

---

## ⚙️ Audio Handling
- **Recording:** Microphone → Binary data → Network
- **Playback:** Network → Binary data → Speakers

---

## 🧵 Concurrency & Threading
- **Server-side:**
    - Main thread for listening to connections
    - One thread per connected client

- **Client-side:**
    - Main thread for GUI (Swing)
    - Separate thread for receiving messages

- **Synchronization:** Shared resources are synchronized to prevent thread conflicts.

---

## ✅ Testing
The following tests were conducted:
- Multiple client connections
- Text message sending/receiving
- Voice message sending/receiving
- Client disconnection handling

---

## 📈 Strengths & Limitations

### ✅ **Strengths**
- Clean and modular architecture
- Clear separation of responsibilities
- Support for both text and voice communication
- Multi-user capability

### ❌ **Limitations & Possible Improvements**
- No data encryption
- Lack of authentication system
- Audio performance can be improved
- Server scalability is limited

---

## 🔮 Future Enhancements
- Add chat rooms/channels
- Integrate video communication
- Implement file sharing
- Develop mobile or web versions

---

## 📂 Project Structure
```
YapChat/
│
├── Serveur.java              # Main server logic
├── ClientHandler.java        # Handles individual clients
├── Client.java               # Network client
├── NetworkController.java    # GUI-network bridge
├── YapChatGUI.java           # User interface
├── Main.java                 # Application entry point
├── Rapport de Projet JAVA.docx # Full project report (French)
└── README.md                 # This file
```

---

## 📚 References
- Java Sockets & Threads Documentation
- Java Sound API
- Java Swing Tutorials

---

## 👨‍💻 Contributors
Developed as a student project for a Java programming course.

---

## 📄 License
This project is for educational purposes.
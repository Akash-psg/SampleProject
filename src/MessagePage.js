import React, { useState } from 'react';
import './MessagePage.css';

const MessagePage = () => {
    const [messages, setMessages] = useState([]);
    const [messageContent, setMessageContent] = useState('');
    const [sender, setSender] = useState(''); // Default or fetched value
    const [receiver, setReceiver] = useState('');
    const [subject, setSubject] = useState('');

    const handleMessageSubmit = async () => {
        if (!sender || !receiver || !subject || !messageContent) {
            alert('Please fill in all fields');
            return;
        }

        const newMessage = {
            sender,
            receiver,
            subject,
            context: messageContent,
            date: new Date().toISOString() 
        };

        try {
            // Post the new message to the server
            const response = await fetch('http://localhost:8080/api/messages', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newMessage)
            });

            if (response.ok) {
                const savedMessage = await response.json();
                setMessages([...messages, savedMessage]); // Update local messages with the saved message
                setMessageContent(''); // Clear the message content field
                setSubject(''); // Clear the subject field
                setReceiver(''); // Clear the receiver field
                setSender(''); // Clear the sender field
            } else {
                alert('Error posting the message.');
            }
        } catch (error) {
            console.error('Error posting message:', error);
            alert('Error posting message.');
        }
    };

    return (
        <div className="message-page">
            <h1>Messages</h1>

            <div className="message-form">
                <h2>Compose a Message</h2>
                <div className="form-group">
                    <label>Sender:</label>
                    <input
                        type="text"
                        value={sender}
                        onChange={(e) => setSender(e.target.value)}
                        placeholder="Enter your name"
                    />
                </div>
                <div className="form-group">
                    <label>Receiver:</label>
                    <input
                        type="text"
                        value={receiver}
                        onChange={(e) => setReceiver(e.target.value)}
                        placeholder="Enter receiver's name"
                    />
                </div>
                <div className="form-group">
                    <label>Subject:</label>
                    <input
                        type="text"
                        value={subject}
                        onChange={(e) => setSubject(e.target.value)}
                        placeholder="Enter subject"
                    />
                </div>
                <div className="form-group">
                    <label>Message:</label>
                    <textarea
                        value={messageContent}
                        onChange={(e) => setMessageContent(e.target.value)}
                        placeholder="Enter your message here..."
                    />
                </div>
                <button onClick={handleMessageSubmit}>Send</button>
            </div>

            <div className="message-list">
                <h2>Message History</h2>
                <ul>
                    {messages.map((message) => (
                        <li key={message.id} className="message-item">
                            <p><strong>From:</strong> {message.sender}</p>
                            <p><strong>To:</strong> {message.receiver}</p>
                            <p><strong>Subject:</strong> {message.subject}</p>
                            <p><strong>Content:</strong> {message.context}</p>
                            <p><strong>Date:</strong> {new Date(message.date).toLocaleString()}</p>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default MessagePage;

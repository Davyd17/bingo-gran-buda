Virtual Bingo Game

Description

This project is an online multiplayer bingo game developed for "El Bingo Gran Buda," a virtual betting and gaming platform.
It allows players to join a live bingo session, receive randomized bingo cards, and mark numbers as they’re drawn in real time.
The game supports multiple winning patterns and includes player authentication and win notifications to keep gameplay fair and engaging.

Features

User Authentication: Supports secure login and registration.

Real-Time Gameplay: Draws numbers every few seconds with live updates for players.

Randomized Bingo Cards: Generates unique cards for each player in each session.

Win Conditions: Players can win by completing a line, "X" pattern, four corners, or a full card.

Lobby Waiting System: Players wait in a lobby for a short time before the game begins.

Notifications: All players are notified when someone wins, ending the game session.

Technologies Used

Backend: Java, Spring Boot, Spring Security, JDBC Template, HikariCP
Database: PostgreSQL, with Flyway for database migrations
Frontend: React, Next.js
Containerization: Docker for deployment and environment consistency
Version Control: Git, GitHub
Installation
Clone the Repository:

Usage

Users can sign up, join a bingo session, and participate in real-time gameplay.
Randomized bingo cards are generated, and players mark numbers as they’re drawn.
Players receive notifications for game outcomes (win/loss) and disqualification on false claims.
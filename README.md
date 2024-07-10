# TeamSynchronizer

TeamSynchronizer is a dashboard for team managers designed to enhance synchronization with geographically dispersed team members. It provides contextual information about each team member, including local news headlines, weather updates, and current time, to increase empathy in the workplace and help managers make better decisions and increase productivity.

## Features

- Add, update, retrieve, and delete team member information
- View real-time weather data and local news headlines for each team member's location
- Dashboard display of team members with snapshots of contextual information
- User authentication and authorization

## Installation

TeamSynchronizer is deployed and accessible via AWS CloudFront. There's no need for local installation to use the application.

To access TeamSynchronizer:

1. Open your web browser
2. Navigate to: https://d2qcetd7rqhjj0.cloudfront.net/
3. Signup then Log in with your provided credentials

## Usage

TeamSynchronizer is a web-based application designed to help team managers better understand and connect with their geographically dispersed team members. Here's how to use the main features:

1. **Login:**
   - Navigate to the TeamSynchronizer homepage
   - Enter your credentials to log in

2. **Home Screen:**
   - After logging in, you'll see a search bar and an "Add Member" button
   - The search bar is used to find your team

3. **Team Search:**
   - Enter your team name in the search bar
   - Click the "Search" button or press Enter
   - This will display a list of all members in the specified team

4. **Team Member Management:**
   - Add new team member: Click the "Add Member" button and fill in their details
   - Edit member info: Select "Edit" from the Actions dropdown next to a member's name
   - Remove a member: Choose "Delete" from the Actions dropdown

5. **Contextual Information:**
   - Weather: Select "Weather" from the Actions dropdown to view current conditions at the member's location
   - Local News: Choose "News" from the Actions dropdown to see top headlines from the member's city

6. **Team Insights:**
   - Use the search results to quickly understand your team's composition
   - Gain cultural context through local news and weather conditions for each team member

Note: Ensure you're logged in to access all features. For any technical issues or feature requests, please send an email to  mohamadacma@gmail.com

## Technologies Used

- AWS Services: API Gateway, Lambda, Cognito, DynamoDB, Secrets Manager
- Third-party APIs: Open-Meteo Weather Forecast API, World News API
- More APIs will be included to get local events and holidays..


# Project Title
TeamInSynch
## Descirption
### Problem statement
Remote Work is here to stay, and talent is everywhere but, there is a problem that presents itself when we're collaborating with varying degrees of georgraphical seperation. Geography is context. How do we foster a sense of connection and understading among team members distributed across different georgraphies? how do we help managers overcome the challenges of managing a georgraphically dispoersed team? 
### Solution
Enter TeamInSynch. 
TeamInSynch is a dashboard for team managers designed to enhance synchronization among team memebrs, it provides the manager with enough contextual information about each memeber of their team, from local news headlines, to weather updates, to current time, so that manager can make better decisions in the worksplace and thus, increase productivity.

## User Stories
U1. 
As a team manager, I'd like to add a team member with all their info( email, joinDate, phoneNumber, Location.. etc).

U2. As a team manager, I'd like to delete a team member.

U3. As a team member, I'd like to sign in to my account and update my location/contact info if needed. (team member can only make updates, no deletion/new additions allowed).

U4. As a team manager, I'd like to retrieve members info by joinDate, location, timeZone, and name

U5.As a team manager, I'd like to see a dashboard with active memebrs on my team and the ability to hover over each memebr and see a snapshot of local context(news headlines, weather info, time).

U5. As a team manager, I'd like to click on any member and see detailed info about local news headlines and local weather conditions at their location.

U6. As a team manager, I'd like to view a constantly changing list of only the active members.

U7. As a team manager, I'd like to setup meetings that automatically takes into consideration the local times of my team members

U8. As a team manager, I'd like to receive notifications if there are any major local news or major weather condition where my team members are located.

U9. As a team memeber, I'd like to set my availability to active or not active.

## Stretch Goals

U1. As a team manager, I'd like the dashboard to display a random fact of the day about each team member's local heritage, changing daily, visible to all team members to enhance cultural awareness and team bonding.

U2. As a team manager, I'd like to reassign members to different teams as the project evolve or change

U3. As a team manager, I'd like to view suggested appointment times that are rendered everyday based on memebr's timezones

## Project Scope
### In scope
* add, update, retreive, and delete a team member using information like name,email,location,joinDate,and phone number. (core functionalities)
* retrieving and displaying weather data and local news headlines at each member's location. (the context needed so that the manager can better understand his team member's environements)
* displaying a dashboard of active members( based on their login status) with snapshots of their real time weather info, current time, and local news headlines. (the dashboard represents the central hub for managers for a quick overview of their team and the relevant context)

### Out of scope
* Daily automatic suggestions of appointment scheduels that consider team members' timezones. ( for future iterations)
* ability to receive notifications in the event of major local news or weather conditions. (good to have, but not key of for the MVP)
* integration with third-party services like emails, fitness, and productivity apps.
* ability to add  teams at different levels in the organization and subteams, so that upper managers and organizations can get relevant data and reports on teams performance metrics, productivity rates, etc. (this project is only for single-level management, multiple-level hierarchies are excluded).

## Proposed Architectural Overview
For the first iteration, we will provide the minimum lovable product (MLP) which includes adding, updating, retrieving, and deleting a team member as well as retrieving weather updates, current time, and local headline news.

we will use API Gateway and Lambda to create the API endpoints( AddTeamMember, UpdateTeamMember, GetTeamMemeber, DeleteTeamMember, GetAllTeamMembers(?))
 
We will authenticate users using AWS cognito

we will store team members in DynamoDB table. As for the weather data and local news, we will retrieve them in real-time from third-party APIs, thereby avoiding storage overhead

Additionally, TeamInSynch will provide its users with a webInterface dashboard style to give managers the ability to manage their teams. A main page providing a list of active members with the ability to hover over each and see snaphots of contextual info will also let them update their team members info and link off to pages per memeber to show detailed profile.

## API
### Public Models
#### //MemberModel
* String Id;

* String name; 

* String email;

* String phoneNumber;

* String Location;

* ZonedDateTime joinDate;

* Boolean isActive;

#### //WeatherModel
* String location;

* ZonedDateTime updatedAt ;

* Double Temperature;

* String weatherCondition;

* String weatherDescription;


#### //NewsModel
* String location

* List<String> headlines;

* ZonedDateTime lastUpdated;

* List<String> sources;

* List<String> URLs;

### Add Team Member Endpoint
* Accepts a POST request to /members
* Accepts data to create a new member with a provided name, a given ID, an email, a phone number, and location . Returns the new member with a unique Id assigned by the Team in synch service. 
##### UML sequence diagram representation:
![Alt text](image.png)

### Update Team Member Endpoint
* Method: PUT
* Path: /members/:id
* Accepts data with memberId to update member info including name,phoneNumber,location, and email.
* Request Body: {"memberId": "string", "email": "string", "location": "string", "phoneNumber": "string"} partial Vs Full
* Response: Returns the updated member info.
* if the memberId is not found, we will throw memberNotFoundException
* for security concerns, we will validate name and location do not contain invalid characters; if that's the case, we will throw an invalidAttributeValueException

### Delete Team Member Endpoint
* Accepts DELETE request to members/:id
* Accepts memeberId to delete memeber associated with it. Return success message.
* if the memberId is not found, we will throw memberNotFoundException
### Get Weather Endpoint
* Accepts GET request to /weather
* Accepts data with location to retrieve current weather information. Returns Weather information specific to the location requested
* if the location is not found, we will throw locationNotFoundException
* if location has invalid date, throw invalidValueAttribution
### Get News Endpoint
* method: GET
* Query Paramters: Location(optional) ?
* path: /news
* Response: returns a list of news headlines
* errors: newsNotFoundException

##### UML class diagram respresentation:
 class TeamMember {
    - String id
    - String name
    - String email
    - String phoneNumber
    - ZonedDateTime UpdatedAt
    - Boolean isActive
 } 
















# 2022_GDSC_project_app-- ChatterBox
Contributors: Nicholas Chan, Selina Fu, Chen-Kuan Liao, Ved Walavalker
DEMO video: https://www.youtube.com/watch?v=bl8KSIxrp9w

## Summary
Built by four students from Pennsylvaia State University during 2022, this project was build to compete in the 2022 Google Solution Challenge, and was able to got into the global top 50 round!

Goals that meet:
- [X] Goal 4: Quality Education 
Specifically, Goal 4, target 7. The app helps to spread awareness about topic that poeple don't usually notice. It also provides a free platform for users to discuss their thoughts on these topics.

## New update functions for the APP
-[X] Our database has been switch from Back4App to Google Firebase Realtime Database
-[X] Users are now able to sign up for an account using our new user interface
-[X] Different activites, room page, post page, swipe page, an others, have been moved into fragments to improve user's experience
-[X] Swipe page, post page, and comment page interfaces have been renewed to improve the user experience

## User Experience Flow Chart
#Sign up page
![alt text](https://github.com/psuPenguins/GDSC_project_app/blob/Firebase/flow%20chart/login%20page.png)



## User Stories

The following functionality is completed before getting in the GDSC 2022 Global top 50:
- [X] Login room
  - [X] Users are able to login, if either the user name or passoword is entered incorrectly, it will prompt a message
  - [X] There are two buttons in the main room to enter the Swipe room and the Question room

- [X] Swipe room 
  - [X] Users are able to see cards containing the questions they might be asked in the Question room
  - [X] Users can swip the cards right and left based on their answers to their answer to the questions
  - [X] Users can go back to the main room by pressing the back botton
 
- [X] Question(Post) room
  - [X] The question of the room should be displayed on the very top of the room
  - [X] Users' posts should be displayed in the screen, and users should be able to scroll down to see all the posts in the room
  - [X] Users can post their thoughts about the questions by pressing the post button on the botton-left corner
  - [X] Users can type their thoughts in the text box and post them once they are finished
  - [X] After posting user's thoughts, they should be link back to the Question room and be able to see their posts
  - [X] Users can upvote and downvote the post based on their opinions about the post
  - [X] Posts should be ordered by their upward votes
 
- [X] Comment room
  - [X] The comments should be ordered by the time it is posted
  - [X] Users can upvote and downvote the comment based on their opinions about it
  - [X] Users can post comments about the post 
  - [X] After posting user's comment, the page should be refreshed and users should be able to see their most recent comments
  

The following features are some furtue features we would like to add:

- [X] We plan on allowing a capability of creating private rooms, where a certain verified room owner, for example teachers, can choose their own topic of discussion and monitor the ongoing discussion between peers for the same
- [X] Users will have the ability to suggest their own questions which will be stored on the database, and depending on the users response, be included in the set of questions people would like to discuss
 

## Video Walkthrough

Here's a walkthrough of implemented user stories:
- [X] Users are able to login, if either the user name or passoword is entered incorrectly, it will prompt a message

<img src='https://github.com/psuPenguins/GDSC_project_app/blob/main/gifs/login_fail.gif' title='Video Walkthrough' width='300px' alt='Video Walkthrough' />

- [X] Users will be able to login and enter different rooms

<img src='https://github.com/psuPenguins/GDSC_project_app/blob/main/gifs/login_back_buttons.gif' title='Video Walkthrough' width='300px' alt='Video Walkthrough' />

- [X] Swipe room

<img src='https://github.com/psuPenguins/GDSC_project_app/blob/main/gifs/swipe.gif' title='Video Walkthrough' width='300px' alt='Video Walkthrough' />

- [X] Question room and adding comments

![Imgur Image](https://i.imgur.com/8LcXaHj.mp4)
GIF created with [Kap](https://getkap.co/).

## Open-source libraries used

- [Firebase Realtime Database] (https://firebase.google.com/) - Set as our backend service
- [PlaceViewHolder](https://github.com/janishar/PlaceHolderView) - Creating the swipe functions in the Swipe room
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android



# TVSeries
 TV series show

# About
 The project uses the TVmaze API to show a wide collection of TV shows and some useful informations
 
# Into the project
 What the project uses:

* Clean Arch
* MVVM
* Retrofit
* Jetpack Navigation
* Binding
* ViewModel
* LiveData
* Follow some material desing rules
* Cool design

# Screenshots

<img src="prints/home.png?raw=true" width="24%" /> <img src="prints/search.png?raw=true" width="24%" /> <img src="prints/show.png?raw=true" width="24%" /> <img src="prints/episode.png?raw=true" width="24%" />


# Deep Link
 You can use the deep-link to open a shared link to open directly the show or an especific episode.
 
### Link samples
* https://api.tvmaze.com/show/detail/eyJpZCI6MTB9
* https://api.tvmaze.com/show/episode/eyJpZCI6MTAsInNlYXNvbiI6MSwibnVtYmVyIjoyfQ==

**To run directly on Android Studio terminal**
* adb shell am start -W -a android.intent.action.VIEW -d "https://api.tvmaze.com/show/detail/eyJpZCI6MTB9" com.example.tvseries
* adb shell am start -W -a android.intent.action.VIEW -d "https://api.tvmaze.com/show/episode/eyJpZCI6MTAsInNlYXNvbiI6MSwibnVtYmVyIjoyfQ==" com.example.tvseries

### Infos

 The parameters are provided by simple json string and encoded in Base64 to easily sharing
 
```
 // Show
 {
   "id": 10
 }
 
 // Episode
 {
   "id": 10,
   "season": 1,
   "number": 2
 }
 
```

<img src="https://github.com/alonsd/BankHapoalimHeroes/blob/main/main_screen.jpeg" width="300"/> <img src="https://github.com/alonsd/BankHapoalimHeroes/blob/main/hero_details_screen.jpeg" width="300"/> 

# Heroes

Bank HaPoalim home task for Android. 
 
- [x] Architecture - Model-View-ViewModel(MVVM). The data flow is UI (Fragments) -> ViewModel -> Repository -> DataSource  -> API
- [x] Business logic is managed in the RemoteDataSource for scalability considerations, if the application grows bigger and uses a LocalDataSource then each data source would handle it's own logic and the repository will only handle merging the results together. 
- [x] SharedPreferences for saving last cache time. 
- [x] Built as a single Activity application ready to scale. 
- [x] Dependency Injection using Koin.
- [x] Networking using Retrofit + Coroutines.

## Authors

* **Alon Shlider** - (https://github.com/alonsd)

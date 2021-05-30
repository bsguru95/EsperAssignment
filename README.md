# Esper Assignment

Esper is an app that fetches mobiles and its related details from api and shows it to user to make valid combinations according to exclusions given


# Libraries used 
 
        Navigation Component 
        Room for database
        Koin for dependancy injection
        retrofit as the REST Client and gson for Json parsing
        Glide for images 

**App's functionality: **

    When user opens app it will try to make network call to fetch data from api and it will store the same in DB in desired manner.
    As soon as the api fetch and DB insertion is successful, the app will fetch data from DB to display in UI.
    UI is filled with chips to provide option to user to make valid combinations
    For each selection of chip, App will check in Exlcusion table for any exclusions for the corresponsing featureID/optionID involved or not
    If there is no exclusions found, app will not make any difference, if there is any exclusions, app will unselect/disable those chips which are not valid
    Submit button will take user to summary screen after selections are made from each feature sections
    DeviceDetails screen will show mobile details along with the addiotnal features/valid combinations made by the user

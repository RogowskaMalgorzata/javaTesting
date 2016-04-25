Given user is on Home page
When user clicks Zaloguj się
Then Zaloguj się page is shown

Given user is on Zaloguj się page
When user gives jellycherry and gosiaczek 
And clicks Zaloguj się button
Then Home page is shown

When user clicks Dyskusja link
Then Dyskusja page is shown

Given user is on Home page
When user searches Olsztyn
Then Olsztyn page is shown

Given user is on Olsztyn page
When user clicks on Muzea link
Then Muzea content is shown

Given user is on Home page
When user clicks Wyloguj
Then Wyloguj page is shown 

Given user is on Home page
When user clicks Zaloguj się
And user gives jellycherry and zlehaslo 
And clicks Zaloguj się button
Then error is shown


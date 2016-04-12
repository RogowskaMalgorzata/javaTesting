Given a messenger 
When set server to inf.ug.edu.pl and message to somemessage
Then sending message should return 0
And testing connection should return 0 or 1

When set server to inf.ug.edu.eu and message to somemessage
Then sending message should return 1
And testing connection should return 1

When set server to inf.ug.edu.pl and message to me 
Then sending message should return 2

When set server to ser and message to somemessage
Then sending message should return 2
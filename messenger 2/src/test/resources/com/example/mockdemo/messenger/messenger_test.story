Given a messenger 
When set server to VALID_SERVER and message to VALID_MESSAGE
Then sending message should return 0

When set server to INVALID_SERVER and message to VALID_MESSAGE
Then sending message should return 1

When message lenght is 2
Then should be MalformedRecipientException

When message lenght is 1
Then should be MalformedRecipientException

When server lenght is 3
Then should be MalformedRecipientException

When server lenght is 0
Then should be MalformedRecipientException
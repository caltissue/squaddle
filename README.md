# Squaddle
###### (working title)

This is a proof-of-concept micro-blogging app I made while learning the Play framework for Java & Scala. I liked Play's use of the FasterXML Jackson library for JSON parsing, which let me serialize easily.

My favorite little method is `IOdevice.getNode` (under `app/classes`). It takes a directory, key, and value, and agnostically fetches a JsonNode.

I got the basic login flow and posting capability up, plus home and an account page. Potential next steps would be:
- Password hashing
- Password recovery flow using email
- Inserting links and media in Posts
- Post replies
- Friend connections

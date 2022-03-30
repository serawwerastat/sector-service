## Sector-selector

##### Tasks:

###### Create a single page application
- Use example-index.html as the basis
- NB! example-index.html has deficiencies so use it only for data and understanding what needs to be present in front-end
- Make it look as good as you can visually in a reasonable time

###### "Sectors" selectbox:
- Add all the entries from the "Sectors" selectbox to database
- Compose the "Sectors" selectbox using data from database

###### Perform the following activities after the "Save" button has been pressed:
- Validate all input data (all fields are mandatory)
- Store all input data to database (Name, Sectors, Agree to terms)
- Refill the form using stored data
- Allow the user to edit his/her own data during the session

### Structure

- sector-selector-ui - UI. Port: 4200. Angular 11
- sector-selector-app - back-end. Port: 8085. Spring 2.6.5
- hibernate DB - starts with back-end with default data

### Running locally

To start locally simply execute `start.cmd` and both ui and service will start in the Docker

### Data base structure

##### Schema:

![schemaImg](https://raw.githubusercontent.com/serawwerastat/sector-service/master/Data_base_schema.png)

Schemas can be also found in `sector-selector-app/src/main/resources/data.sql`

##### Initial data:

Initial DB data can be found in `sector-selector-app/src/main/resources/schema.sql`

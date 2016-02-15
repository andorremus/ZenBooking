INSERT INTO AccommodationType VALUES(1,"Hotel");
INSERT INTO AccommodationType VALUES(2,"Youth Hostel");
INSERT INTO AccommodationType VALUES(3,"Villa");
INSERT INTO AccommodationType VALUES(4,"Bed and Breakfast");
INSERT INTO AccommodationType VALUES(5,"Ski Resort");

INSERT INTO Transport VALUES (1,"Plane");
INSERT INTO Transport VALUES (2,"Ferry");

INSERT INTO City VALUES(1,"Paris",1);
INSERT INTO City VALUES(2,"Crete",2);
INSERT INTO City VALUES(3,"Dubrovnik",1);
INSERT INTO City VALUES(4,"Geneve",1);

INSERT INTO Extras VALUES(1,"Scuba Diving",25);
INSERT INTO Extras VALUES(2,"Ski Rental",10);
INSERT INTO Extras VALUES(3,"Travel Insurance",20);
INSERT INTO Extras VALUES(4,"Snorkeling",25);
INSERT INTO Extras VALUES(5,"Airport Transfer",25);
INSERT INTO Extras VALUES(6,"Extra Luggage",25);

INSERT INTO CityHasExtras VALUES(1,3);
INSERT INTO CityHasExtras VALUES(1,5);
INSERT INTO CityHasExtras VALUES(1,6);
INSERT INTO CityHasExtras VALUES(2,4);
INSERT INTO CityHasExtras VALUES(2,3);
INSERT INTO CityHasExtras VALUES(3,3);
INSERT INTO CityHasExtras VALUES(3,5);
INSERT INTO CityHasExtras VALUES(3,1);
INSERT INTO CityHasExtras VALUES(3,6);
INSERT INTO CityHasExtras VALUES(4,2);
INSERT INTO CityHasExtras VALUES(4,5);
INSERT INTO CityHasExtras VALUES(4,6);



/* Paris Hotel */
INSERT INTO BaseHolidayPrice VALUES(1,7,1,499);
INSERT INTO BaseHolidayPrice VALUES(1,10,1,599);
INSERT INTO BaseHolidayPrice VALUES(1,14,1,699);

/* Paris Youth Hostel */
INSERT INTO BaseHolidayPrice VALUES(1,7,2,199);
INSERT INTO BaseHolidayPrice VALUES(1,10,2,249);
INSERT INTO BaseHolidayPrice VALUES(1,14,2,329);

/* Crete Hotel */
INSERT INTO BaseHolidayPrice VALUES(2,7,1,449);
INSERT INTO BaseHolidayPrice VALUES(2,10,1,549);
INSERT INTO BaseHolidayPrice VALUES(2,14,1,659);

/* Crete Villa */
INSERT INTO BaseHolidayPrice VALUES(2,7,3,319);
INSERT INTO BaseHolidayPrice VALUES(2,10,3,399);
INSERT INTO BaseHolidayPrice VALUES(2,14,3,449);

/* Dubrovnik Bed and Breakfast */
INSERT INTO BaseHolidayPrice VALUES(3,7,4,259);
INSERT INTO BaseHolidayPrice VALUES(3,10,4,299);
INSERT INTO BaseHolidayPrice VALUES(3,14,4,359);

/* Dubrovnik Villa */
INSERT INTO BaseHolidayPrice VALUES(3,7,3,199);
INSERT INTO BaseHolidayPrice VALUES(3,10,3,250);
INSERT INTO BaseHolidayPrice VALUES(3,14,3,375);

/* Adelboden Hotel */
INSERT INTO BaseHolidayPrice VALUES(4,7,1,400);
INSERT INTO BaseHolidayPrice VALUES(4,10,1,539);
INSERT INTO BaseHolidayPrice VALUES(4,14,1,679);

/* Adelboden Chalet */
INSERT INTO BaseHolidayPrice VALUES(4,7,5,199);
INSERT INTO BaseHolidayPrice VALUES(4,10,5,249);
INSERT INTO BaseHolidayPrice VALUES(4,14,5,299);









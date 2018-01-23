--liquibase formatted sql
--changeset kabasakalis:060

-- Roles -------------------------------------------------------------------------------

insert into roles (id, name) values (1, 'ADMIN');
insert into roles (id, name) values (2, 'MODERATOR');
insert into roles (id, name) values (3, 'USER');

-- Users -------------------------------------------------------------------------------

-- insert into users (id, username, email, password) values (1, 'admin', 'admin@springify.com', '$2a$10$UClj/ChiXzRXcT6HvSdOpePZFzTIJs9CYDagygovYbM0TDzMRe7Di');
insert into users (id, username, email, password) values (2, 'moderator', 'mod@springify.com', '$2a$10$.JWu1W6bpa1tlMtGKUt50uF8Gkd01sNme9D4PPryRXR6sjZ9pXYrG');
insert into users (id, username, email, password) values (3, 'peter', 'zourdos@gmail.com', '$2a$10$eAQhN15Bz7liEDjmzohBBekENkn73AzQqW9ILsJDWaE6QCeK90eDu' );
insert into users (id, username, email, password) values (4, 'moderator2', 'moderator2@springify.com', '$2a$10$UClj/ChiXzRXcT6HvSdOpePZFzTIJs9CYDagygovYbM0TDzMRe7Di' );


-- Assign Users to Roles -------------------------------------------------------------------------------

-- insert into users_roles (user_id, role_id) values (1, 1);
-- insert into users_roles (user_id, role_id) values (1, 2);
-- insert into users_roles (user_id, role_id) values (1, 3);

-- insert into users_roles (user_id, role_id) values (4, 1);
insert into users_roles (user_id, role_id) values (4, 2);
insert into users_roles (user_id, role_id) values (4, 3);

insert into users_roles (user_id, role_id) values (2, 2);
insert into users_roles (user_id, role_id) values (2, 3);

insert into users_roles (user_id, role_id) values (3, 3);



-- Genres -------------------------------------------------------------------------------

insert into genres (id, name ) values (1, 'Rock');
insert into genres (id, name ) values (2, 'Metal');
insert into genres (id, name ) values (3, 'Pop');
insert into genres (id, name ) values (4, 'Rap');
insert into genres (id, name) values (5, 'Trap');
insert into genres (id, name) values (6, 'Soundtrack');
insert into genres (id, name) values (7, 'Eurodance');


-- Artists -------------------------------------------------------------------------------

 -- Metal Bands
insert into artists (id,genre_id, name, country) values (1,2, 'King Diamond', 'Denmark');
insert into artists (id,genre_id, name, country) values (2, 2,'Iron Maiden', 'UK');
insert into artists (id,genre_id, name, country) values (3, 2,'Judas Priest', 'UK');
insert into artists (id,genre_id, name, country) values (4, 2,'Savatage', 'USA');
insert into artists (id,genre_id, name, country) values (5, 2,'Metallica', 'USA');
insert into artists (id,genre_id, name, country) values (6, 2,'Ozzy Osbourne', 'USA');
insert into artists (id,genre_id, name, country) values (7, 2,'Dio', 'USA');
insert into artists (id,genre_id, name, country) values (8, 2,'Dream Theater', 'USA');
insert into artists (id,genre_id, name, country) values (9, 2,'Pantera', 'USA');
insert into artists (id,genre_id, name, country) values (10, 2,'Megadeth', 'USA');

 -- Rock Bands
insert into artists (id,genre_id, name, country) values (11,1, 'AC/DC', 'Australia');
insert into artists (id,genre_id, name, country) values (12, 1,'Van Halen', 'USA');
insert into artists (id,genre_id, name, country) values (13, 1,'Def Leppard', 'UK');
insert into artists (id,genre_id, name, country) values (14, 1,'Kip Winger', 'USA');
insert into artists (id,genre_id, name, country) values (15, 1,'Foreigner', 'USA');
insert into artists (id,genre_id, name, country) values (16, 1,'Supertramp', 'USA');
insert into artists (id,genre_id, name, country) values (17, 1,'Queen', 'USA');
insert into artists (id,genre_id, name, country) values (18, 1,'ZZ Top', 'USA');
insert into artists (id,genre_id, name, country) values (19, 1,'Kiss', 'USA');
insert into artists (id,genre_id, name, country) values (20, 1,'Rush', 'USA');

 -- Pop Artists
insert into artists (id,genre_id, name, country) values (21,3, 'Katy Perry', 'USA');
insert into artists (id,genre_id, name, country) values (22, 3,'Justin Timberlake', 'USA');
insert into artists (id,genre_id, name, country) values (23, 3,'Justin Bieber', 'UK');
insert into artists (id,genre_id, name, country) values (24, 3,'Dua Lipa', 'Albania');
insert into artists (id,genre_id, name, country) values (25, 3,'Christina Aguilera', 'USA');
insert into artists (id,genre_id, name, country) values (26, 3,'Rihanna', 'USA');
insert into artists (id,genre_id, name, country) values (27, 3,'Talor Swift', 'USA');

 -- Rap Artists
insert into artists (id,genre_id, name, country) values (28,4, 'Yelawolf', 'USA');
insert into artists (id,genre_id, name, country) values (29, 4,'Eminem', 'USA');
insert into artists (id,genre_id, name, country) values (30, 4,'Rittz', 'USA');
insert into artists (id,genre_id, name, country) values (31, 4,'Missy Elliot', 'USA');
insert into artists (id,genre_id, name, country) values (100, 4,'Nicki Minaj', 'USA');

 -- Trap Artists
insert into artists (id,genre_id, name, country) values (32, 5,'Migos', 'USA');
insert into artists (id,genre_id, name, country) values (33, 5,'Cardi B', 'USA');
insert into artists (id,genre_id, name, country) values (34, 5,'Post Malone', 'USA');

 -- Soundrack Artists
insert into artists (id,genre_id, name, country) values (35, 6,'Brian Tyler', 'USA');
insert into artists (id,genre_id, name, country) values (36, 6,'John Powell', 'USA');
insert into artists (id,genre_id, name, country) values (37, 6,'Hans Zimmerman', 'USA');
insert into artists (id,genre_id, name, country) values (38, 6,'Junkie XL', 'USA');



-- Albums  -------------------------------------------------------------------------------

-- King Diamond
insert into albums (id,artist_id, title, year) values (1, 1, 'Fatal Portrait', '1986');
insert into albums (id,artist_id, title, year) values (2, 1, 'Abigail', '1987');
insert into albums (id,artist_id, title, year) values (3, 1, 'Them', '1988');
insert into albums (id,artist_id, title, year) values (4, 1, 'Conspiracy', '1990');

-- Iron Maiden
insert into albums (id,artist_id, title, year) values (5, 2, 'The Number Of The  Beast', '1982');
insert into albums (id,artist_id, title, year) values (6, 2, 'Piece Of Mind', '1983');
insert into albums (id,artist_id, title, year) values (7, 2, 'Powerslave', '1984');
insert into albums (id,artist_id, title, year) values (8, 2, 'Somewhere in Time', '1986');

--  Judas Priest
insert into albums (id,artist_id, title, year) values (9, 3, 'Painkiller', '1990');
insert into albums (id,artist_id, title, year) values (10, 3, 'Screaming For Vengeance', '1982');
insert into albums (id,artist_id, title, year) values (11, 3, 'Defenders Of The Faith', '1984');
insert into albums (id,artist_id, title, year) values (12, 3, 'Turbo Lover', '1986');

--  Dream Theater
insert into albums (id,artist_id, title, year) values (13, 8, 'Images And Words', '1992');
insert into albums (id,artist_id, title, year) values (14, 8, 'Metropolis Pt2: Scenes From A Memory', '1999');
insert into albums (id,artist_id, title, year) values (15, 8, 'Dream Theater', '2013');
insert into albums (id,artist_id, title, year) values (16, 8, 'Train Of Thought', '2003');

--  AC/DC
insert into albums (id,artist_id, title, year) values (17, 11, 'Back in Black', '1980');
insert into albums (id,artist_id, title, year) values (18, 11, 'For Those About To Rock', '1981');
insert into albums (id,artist_id, title, year) values (19, 11, 'Flick Of The Switch', '1983');
insert into albums (id,artist_id, title, year) values (20, 11, 'Fly On The Wall', '1985');


--  Van Halen
insert into albums (id,artist_id, title, year) values (21, 12, '1984', '1984');
insert into albums (id,artist_id, title, year) values (22, 12, '5150', '1986');
insert into albums (id,artist_id, title, year) values (23, 12, 'For Unlawful Carnal Knowledge', '1991');
insert into albums (id,artist_id, title, year) values (24, 12, 'OU812', '1988');

-- Def Leppard
insert into albums (id,artist_id, title, year) values (25, 13, 'Pyromania', '1983');
insert into albums (id,artist_id, title, year) values (26, 13, 'Hysteria', '1987');
insert into albums (id,artist_id, title, year) values (27, 13, 'Adrenalize', '1992');
insert into albums (id,artist_id, title, year) values (101, 13, 'On Through The Night', '1980');

-- Kip Winger
insert into albums (id,artist_id, title, year) values (28, 14, 'Karma', '2009');
insert into albums (id,artist_id, title, year) values (29, 14, 'Winger', '1988');


--  Foreigner
insert into albums (id,artist_id, title, year) values (30, 15, 'Mr Moonlight', '1994');
insert into albums (id,artist_id, title, year) values (31, 15, 'Unusual Heat', '1988');

--  Supertramp
insert into albums (id,artist_id, title, year) values (32, 16, 'Breakfast In America', '1979');
insert into albums (id,artist_id, title, year) values (33, 16, 'Paris', '1980');

--  Queen
insert into albums (id,artist_id, title, year) values (34, 17, 'Flash Gordon', '1989');
insert into albums (id,artist_id, title, year) values (35, 17, 'Live Killers', '1979');

--  Katy Perry
insert into albums (id,artist_id, title, year) values (36, 21, 'Witness', '2017');
insert into albums (id,artist_id, title, year) values (37, 21, 'Prism', '201');

--  Christina Aguilera
insert into albums (id,artist_id, title, year) values (38, 25, 'Stripped', '2002');
insert into albums (id,artist_id, title, year) values (39, 25, 'Bionic', '2010');
insert into albums (id,artist_id, title, year) values (40, 25, 'Lotus', '2010');

--  Yelawolf
insert into albums (id,artist_id, title, year) values (41, 28, 'Trial By Fire', '2017');
insert into albums (id,artist_id, title, year) values (42, 28, 'Trunk Muzik', '2010');
insert into albums (id,artist_id, title, year) values (43, 28, 'Radioactive', '2011');
insert into albums (id,artist_id, title, year) values (44, 28, 'Lovestory', '2015');

--  Eminem
insert into albums (id,artist_id, title, year) values (45, 29, 'The Eminem Show', '2002');
insert into albums (id,artist_id, title, year) values (46, 29, 'The Marshall Mathers LP', '2000');
insert into albums (id,artist_id, title, year) values (47, 29, 'Encore', '2004');
insert into albums (id,artist_id, title, year) values (48, 29, 'Infinite', '1996');


--  Rittz
insert into albums (id,artist_id, title, year) values (49, 30, 'Next To Nothing', '2014');
insert into albums (id,artist_id, title, year) values (50, 30, 'Top Of The Line', '2016');
insert into albums (id,artist_id, title, year) values (51, 30, 'Last Call', '2017');


-- Migos
insert into albums (id,artist_id, title, year) values (103, 32, 'Migo Thuggin', '2016');
insert into albums (id,artist_id, title, year) values (52, 32, 'No Label 2', '2014');
insert into albums (id,artist_id, title, year) values (53, 32, 'Yung Rich Nation', '2015');

-- Cardi B
insert into albums (id,artist_id, title, year) values (54, 33, 'Gangsta Bitch Music Vol 1', '2016');
insert into albums (id,artist_id, title, year) values (55, 33, 'Gangsta Bitch Music Vol 2', '2017');

-- Post Malone
insert into albums (id,artist_id, title, year) values (56, 34, 'Stoney', '2016');
insert into albums (id,artist_id, title, year) values (57, 34, 'Beerbongs & Bentleys', '2017');

-- Brian Tyler
insert into albums (id,artist_id, title, year) values (58, 35, 'Avengers Age Of Ultron', '2015');
insert into albums (id,artist_id, title, year) values (59, 35, 'Iron Man', '2013');
insert into albums (id,artist_id, title, year) values (60, 35, 'Furious 7', '2015');
insert into albums (id,artist_id, title, year) values (61, 35, 'Battle Of Los Angeles', '2011');
insert into albums (id,artist_id, title, year) values (62, 35, 'Now You See Me 2', '2016');

-- John Powell
insert into albums (id,artist_id, title, year) values (63, 36, 'How To Train Your Dragon', '2010');
insert into albums (id,artist_id, title, year) values (64, 36, 'The Bourne Supremacy', '2004');

-- Junkie XL
insert into albums (id,artist_id, title, year) values (65, 38, 'Mad Max: Fury Road', '2015');
insert into albums (id,artist_id, title, year) values (66, 38, 'Deadpool', '2016');
insert into albums (id,artist_id, title, year) values (67, 38, '300: Rise Of An Empire', '2014');


-- Playlists  -------------------------------------------------------------------------------

insert into playlists (id, name) values (1, 'Global Top 50');
insert into playlists (id, name) values (2, 'USA Top 50');
insert into playlists (id, name) values (3, 'Classic Rock');
insert into playlists (id, name) values (4, 'Rock Anthems');
insert into playlists (id, name) values (5, 'Essential Metal');
insert into playlists (id, name) values (6, 'Metal Anthems');
insert into playlists (id, name) values (7, 'Essential Pop');
insert into playlists (id, name) values (8, 'Latest Pop');
insert into playlists (id, name) values (9, 'Soundtrack');
insert into playlists (id, name) values (10, 'Orchestral');
insert into playlists (id, name) values (11, 'Rap Anthems');
insert into playlists (id, name) values (12, 'Rap Essentials');
insert into playlists (id, name) values (13, 'Trap');



-- Put Albums To Playlists  -------------------------------------------------------------------------------


-- Metal Playlists

insert into playlists_albums (playlist_id, album_id) values (5, 1);
insert into playlists_albums (playlist_id, album_id) values (5, 2);
insert into playlists_albums (playlist_id, album_id) values (5, 3);
insert into playlists_albums (playlist_id, album_id) values (5, 4);
insert into playlists_albums (playlist_id, album_id) values (5, 5);
insert into playlists_albums (playlist_id, album_id) values (5, 6);
insert into playlists_albums (playlist_id, album_id) values (5, 7);
insert into playlists_albums (playlist_id, album_id) values (5, 8);
insert into playlists_albums (playlist_id, album_id) values (5, 9);
insert into playlists_albums (playlist_id, album_id) values (5, 10);
insert into playlists_albums (playlist_id, album_id) values (5, 11);
insert into playlists_albums (playlist_id, album_id) values (5, 12);
insert into playlists_albums (playlist_id, album_id) values (5, 13);
insert into playlists_albums (playlist_id, album_id) values (5, 14);
insert into playlists_albums (playlist_id, album_id) values (5, 15);
insert into playlists_albums (playlist_id, album_id) values (5, 16);


insert into playlists_albums (playlist_id, album_id) values (6, 1);
insert into playlists_albums (playlist_id, album_id) values (6, 2);
insert into playlists_albums (playlist_id, album_id) values (6, 3);
insert into playlists_albums (playlist_id, album_id) values (6, 4);
insert into playlists_albums (playlist_id, album_id) values (6, 5);
insert into playlists_albums (playlist_id, album_id) values (6, 6);
insert into playlists_albums (playlist_id, album_id) values (6, 7);
insert into playlists_albums (playlist_id, album_id) values (6, 8);
insert into playlists_albums (playlist_id, album_id) values (6, 9);
insert into playlists_albums (playlist_id, album_id) values (6, 10);
insert into playlists_albums (playlist_id, album_id) values (6, 11);
insert into playlists_albums (playlist_id, album_id) values (6, 12);
insert into playlists_albums (playlist_id, album_id) values (6, 13);
insert into playlists_albums (playlist_id, album_id) values (6, 14);
insert into playlists_albums (playlist_id, album_id) values (6, 15);
insert into playlists_albums (playlist_id, album_id) values (6, 16);


-- Rock Playlists

insert into playlists_albums (playlist_id, album_id) values (3, 17);
insert into playlists_albums (playlist_id, album_id) values (3, 18);
insert into playlists_albums (playlist_id, album_id) values (3, 19);
insert into playlists_albums (playlist_id, album_id) values (3, 20);
insert into playlists_albums (playlist_id, album_id) values (3, 21);
insert into playlists_albums (playlist_id, album_id) values (3, 22);
insert into playlists_albums (playlist_id, album_id) values (3, 23);
insert into playlists_albums (playlist_id, album_id) values (3, 24);
insert into playlists_albums (playlist_id, album_id) values (3, 25);
insert into playlists_albums (playlist_id, album_id) values (3, 26);
insert into playlists_albums (playlist_id, album_id) values (3, 27);
insert into playlists_albums (playlist_id, album_id) values (3, 28);
insert into playlists_albums (playlist_id, album_id) values (3, 29);
insert into playlists_albums (playlist_id, album_id) values (3, 30);
insert into playlists_albums (playlist_id, album_id) values (3, 31);
insert into playlists_albums (playlist_id, album_id) values (3, 32);
insert into playlists_albums (playlist_id, album_id) values (3, 33);
insert into playlists_albums (playlist_id, album_id) values (3, 34);
insert into playlists_albums (playlist_id, album_id) values (3, 35);


insert into playlists_albums (playlist_id, album_id) values (4, 17);
insert into playlists_albums (playlist_id, album_id) values (4, 18);
insert into playlists_albums (playlist_id, album_id) values (4, 19);
insert into playlists_albums (playlist_id, album_id) values (4, 20);
insert into playlists_albums (playlist_id, album_id) values (4, 21);
insert into playlists_albums (playlist_id, album_id) values (4, 22);
insert into playlists_albums (playlist_id, album_id) values (4, 23);
insert into playlists_albums (playlist_id, album_id) values (4, 24);
insert into playlists_albums (playlist_id, album_id) values (4, 25);
insert into playlists_albums (playlist_id, album_id) values (4, 26);
insert into playlists_albums (playlist_id, album_id) values (4, 27);
insert into playlists_albums (playlist_id, album_id) values (4, 28);
insert into playlists_albums (playlist_id, album_id) values (4, 29);
insert into playlists_albums (playlist_id, album_id) values (4, 30);
insert into playlists_albums (playlist_id, album_id) values (4, 31);
insert into playlists_albums (playlist_id, album_id) values (4, 32);
insert into playlists_albums (playlist_id, album_id) values (4, 33);
insert into playlists_albums (playlist_id, album_id) values (4, 34);
insert into playlists_albums (playlist_id, album_id) values (4, 35);


-- Pop Playlists

insert into playlists_albums (playlist_id, album_id) values (1, 36);
insert into playlists_albums (playlist_id, album_id) values (1, 37);
insert into playlists_albums (playlist_id, album_id) values (1, 38);
insert into playlists_albums (playlist_id, album_id) values (1, 39);
insert into playlists_albums (playlist_id, album_id) values (1, 40);


insert into playlists_albums (playlist_id, album_id) values (2, 36);
insert into playlists_albums (playlist_id, album_id) values (2, 37);
insert into playlists_albums (playlist_id, album_id) values (2, 38);
insert into playlists_albums (playlist_id, album_id) values (2, 39);
insert into playlists_albums (playlist_id, album_id) values (2, 40);

insert into playlists_albums (playlist_id, album_id) values (7, 36);
insert into playlists_albums (playlist_id, album_id) values (7, 37);
insert into playlists_albums (playlist_id, album_id) values (7, 38);
insert into playlists_albums (playlist_id, album_id) values (7, 39);
insert into playlists_albums (playlist_id, album_id) values (7, 40);

insert into playlists_albums (playlist_id, album_id) values (8, 36);
insert into playlists_albums (playlist_id, album_id) values (8, 37);
insert into playlists_albums (playlist_id, album_id) values (8, 38);
insert into playlists_albums (playlist_id, album_id) values (8, 39);
insert into playlists_albums (playlist_id, album_id) values (8, 40);

-- Rap Playlists

insert into playlists_albums (playlist_id, album_id) values (11, 41);
insert into playlists_albums (playlist_id, album_id) values (11, 42);
insert into playlists_albums (playlist_id, album_id) values (11, 43);
insert into playlists_albums (playlist_id, album_id) values (11, 44);
insert into playlists_albums (playlist_id, album_id) values (11, 45);
insert into playlists_albums (playlist_id, album_id) values (11, 46);
insert into playlists_albums (playlist_id, album_id) values (11, 47);
insert into playlists_albums (playlist_id, album_id) values (11, 48);
insert into playlists_albums (playlist_id, album_id) values (11, 49);
insert into playlists_albums (playlist_id, album_id) values (11, 50);
insert into playlists_albums (playlist_id, album_id) values (11, 51);

insert into playlists_albums (playlist_id, album_id) values (12, 41);
insert into playlists_albums (playlist_id, album_id) values (12, 42);
insert into playlists_albums (playlist_id, album_id) values (12, 43);
insert into playlists_albums (playlist_id, album_id) values (12, 44);
insert into playlists_albums (playlist_id, album_id) values (12, 45);
insert into playlists_albums (playlist_id, album_id) values (12, 46);
insert into playlists_albums (playlist_id, album_id) values (12, 47);
insert into playlists_albums (playlist_id, album_id) values (12, 48);
insert into playlists_albums (playlist_id, album_id) values (12, 49);
insert into playlists_albums (playlist_id, album_id) values (12, 50);
insert into playlists_albums (playlist_id, album_id) values (12, 51);

-- Trap Playlists

insert into playlists_albums (playlist_id, album_id) values (1, 52);
insert into playlists_albums (playlist_id, album_id) values (1, 53);
insert into playlists_albums (playlist_id, album_id) values (1, 54);
insert into playlists_albums (playlist_id, album_id) values (1, 55);
insert into playlists_albums (playlist_id, album_id) values (1, 56);
insert into playlists_albums (playlist_id, album_id) values (1, 57);
insert into playlists_albums (playlist_id, album_id) values (1, 103);

insert into playlists_albums (playlist_id, album_id) values (2, 52);
insert into playlists_albums (playlist_id, album_id) values (2, 53);
insert into playlists_albums (playlist_id, album_id) values (2, 54);
insert into playlists_albums (playlist_id, album_id) values (2, 55);
insert into playlists_albums (playlist_id, album_id) values (2, 56);
insert into playlists_albums (playlist_id, album_id) values (2, 57);
insert into playlists_albums (playlist_id, album_id) values (2, 103);

insert into playlists_albums (playlist_id, album_id) values (13, 52);
insert into playlists_albums (playlist_id, album_id) values (13, 53);
insert into playlists_albums (playlist_id, album_id) values (13, 54);
insert into playlists_albums (playlist_id, album_id) values (13, 55);
insert into playlists_albums (playlist_id, album_id) values (13, 56);
insert into playlists_albums (playlist_id, album_id) values (13, 57);
insert into playlists_albums (playlist_id, album_id) values (13, 103);

-- Soundtrack Playlists

insert into playlists_albums (playlist_id, album_id) values (9, 58);
insert into playlists_albums (playlist_id, album_id) values (9, 59);
insert into playlists_albums (playlist_id, album_id) values (9, 60);
insert into playlists_albums (playlist_id, album_id) values (9, 61);
insert into playlists_albums (playlist_id, album_id) values (9, 62);
insert into playlists_albums (playlist_id, album_id) values (9, 63);
insert into playlists_albums (playlist_id, album_id) values (9, 64);
insert into playlists_albums (playlist_id, album_id) values (9, 65);
insert into playlists_albums (playlist_id, album_id) values (9, 66);
insert into playlists_albums (playlist_id, album_id) values (9, 67);


insert into playlists_albums (playlist_id, album_id) values (10, 58);
insert into playlists_albums (playlist_id, album_id) values (10, 59);
insert into playlists_albums (playlist_id, album_id) values (10, 60);
insert into playlists_albums (playlist_id, album_id) values (10, 61);
insert into playlists_albums (playlist_id, album_id) values (10, 62);
insert into playlists_albums (playlist_id, album_id) values (10, 63);
insert into playlists_albums (playlist_id, album_id) values (10, 64);
insert into playlists_albums (playlist_id, album_id) values (10, 65);
insert into playlists_albums (playlist_id, album_id) values (10, 66);
insert into playlists_albums (playlist_id, album_id) values (10, 67);

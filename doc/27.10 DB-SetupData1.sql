﻿-- SalesWebApp
-- Grunddaten für die DB
INSERT INTO `findlunchandswa`.`country` (`country_code`, `name`) VALUES ('DE', 'Deutschland');
INSERT INTO `findlunchandswa`.`country` (`country_code`, `name`) VALUES ('UK', 'United Kingdom');

INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (1, 'Montag', 1);
INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (2, 'Dienstag', 2);
INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (3, 'Mittwoch', 3);
INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (4, 'Donnerstag', 4);
INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (5, 'Freitag', 5);
INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (6, 'Samstag', 6);
INSERT INTO `findlunchandswa`.`day_of_week` (`id`, `name`, `day_number`) VALUES (7, 'Sonntag', 7);

INSERT INTO `findlunchandswa`.`kitchen_type` (`id`, `name`) VALUES (1, 'Italienisch');
INSERT INTO `findlunchandswa`.`kitchen_type` (`id`, `name`) VALUES (2, 'Indisch');
INSERT INTO `findlunchandswa`.`kitchen_type` (`id`, `name`) VALUES (3, 'Griechisch');
INSERT INTO `findlunchandswa`.`kitchen_type` (`id`, `name`) VALUES (4, 'Asiatisch');
INSERT INTO `findlunchandswa`.`kitchen_type` (`id`, `name`) VALUES (5, 'Bayerisch');

INSERT INTO `findlunchandswa`.`restaurant_type` (`id`, `name`) VALUES (1, 'Imbiss');
INSERT INTO `findlunchandswa`.`restaurant_type` (`id`, `name`) VALUES (2, 'Restaurant');
INSERT INTO `findlunchandswa`.`restaurant_type` (`id`, `name`) VALUES (3, 'Bäckerei');
INSERT INTO `findlunchandswa`.`restaurant_type` (`id`, `name`) VALUES (4, 'Sonstiges');

INSERT INTO `findlunchandswa`.`user_type` (`id`,`name`) VALUES (1, "Anbieter");
INSERT INTO `findlunchandswa`.`user_type` (`id`,`name`) VALUES (2, "Kunde");
INSERT INTO `findlunchandswa`.`user_type` (`id`,`name`) VALUES (3, "Betreiber");

INSERT INTO `findlunchandswa`.`euro_per_point` (`id`,`euro`) VALUES (1, 1.0);

INSERT INTO `findlunchandswa`.`minimum_profit` (`id`,`profit`) VALUES (1, 10);

INSERT INTO `findlunchandswa`.`account_type` (`id`,`name`) VALUES (1, 'Forderungskonto');
INSERT INTO `findlunchandswa`.`account_type` (`id`,`name`) VALUES (2, 'Kundenkonto');

INSERT INTO `findlunchandswa`.`booking_reason` (`id`,`reason`) VALUES (1, 'Forderung');
INSERT INTO `findlunchandswa`.`booking_reason` (`id`,`reason`) VALUES (2, 'Einzahlung');

INSERT INTO `findlunchandswa`.`swa_todo_request_typ` (`id`, `name`) VALUES (1, 'Änderung freigeben');
INSERT INTO `findlunchandswa`.`swa_todo_request_typ` (`id`, `name`) VALUES (2, 'Umsatzwarnung');
INSERT INTO `findlunchandswa`.`swa_todo_request_typ` (`id`, `name`) VALUES (3, 'Besuchsaufforderung');

INSERT INTO `findlunchandswa`.`additives` (`id`, `name`, `description`, `short`) VALUES 
(1, 'Farbstoffe E 100 - E 180', 'mit Farbstoff', 'a'), 
(2, 'Konservierungsstoffe E 200 - E 219, E 230 - E 235, E 239, E 249 - E 252, E 280 - E 285, E 1105', 'mit Konservierungsstoff', 'b'),
(3, 'Geschmacksverstärker E 620 - E 635', 'mit Geschmacksverstärker', 'c'), 
(4, 'Schwefeldioxid/Sulfite E 220 - E 228 ab 10 mg/kg', 'geschwefelt', 'd'), 
(5, 'Eisensalze E 579, E 585', 'geschwärzt', 'e'), 
(6, 'Stoffe zur Oberflächenbehandlung E 901 - E 904, E 912, E 914', 'gewachst', 'f'), 
(7, 'Süßstoffe E 950 - E 952, E 954, E 955, E 957, E 959, E 962', 'mit Süßungsmittel(n)', 'g'), 
(8, 'andere Süßungsmittel mit mehr als 10% Gehalt (Zuckeralkohole) E 420, E 421, E 953, E 965 - E 967', 'kann bei übermäßigem Verzehr abführend wirken', 'h'), 
(9, 'Phosphate E 338 - 341, E 450 - E 452', 'mit Phosphat', 'i'), 
(10, 'Coffein', 'coffeinhaltig', 'j'), 
(11, 'Chinin, Chininsalze', 'chininhaltig', 'k'), 
(12, 'Taurin', 'taurinhaltig', 'l'), 
(13, 'Zutaten mit gentechnisch veränderten Organismen', 'gentechnisch verändert', 'm'), 
(14, 'Gentechnisch veränderte Organismen', 'enthält Sojaöl, aus gentechnisch veränderter Soja hergestellt', 'n');

INSERT INTO `findlunchandswa`.`allergenic` (`id`, `name`, `description`, `short`) VALUES 
(1, 'Getreideprodukte (Glutenhaltig)', 'Weizen, Roggen, Gerste, Hafer, Dinkel, Kamut und daraus hergestellte Erzeugnisse, also Stärke, Brot, Nudeln, Panaden, Wurstwaren, Desserts etc.. Ausgenommen sind Glukosesirup auf Weizen- und Gerstenbasis. ', '1'), 
(2, 'Fisch', 'Betroffen sind alle Süß- und Salzwasserfischarten, Kaviar, Fischextrakte, Würzpasten, Saucen etc.. Ganz genau genommen müsste auch ausgewiesen werden, wenn Produkte von Tieren verarbeitet werden, die mit Fischmehl gefüttert wurden.', '2'), 
(3, 'Krebstiere', 'Garnelen, Hummer, Krebse, Scampi, Shrimps, Langusten und sämtliche daraus gewonnenen Erzeugnisse. Wer also in seinen Gerichten asiatische Gewürzmischung oder Paste mit Extrakten aus Krebstieren verwendet, muss das deklarieren. ', '3'), 
(4, 'Schwefeldioxide und Sulfite', 'Wie sie in Softdrinks, Bier, Wein, Essig, Trockenfrüchten und bei diversen Fleisch-, Fisch- und Gemüseprodukten entstehen oder zugesetzt werden, in Konzentrationen von mehr als 10 mg/Kg oder 10 mg/l als insgesamt vorhandenes Schwefeldioxid.', '4'), 
(5, 'Sellerie', 'Sowohl Knolle als auch Staude müssen deklariert werden, egal in welchem Aggregatzustand sie zum Gast wandern. Als Gewürz in Fertiggerichten, in Dressings, Ketchup, Saucen.', '5'), 
(6, 'Milch und Laktose', 'Erzeugnisse wie Butter, Käse, Margarine etc. und Produkte, in denen Milch und /oder Laktose vorkommt, also etwa Brot-, Backwaren, Wurstwaren, Pürees, Suppen oder Saucen. Ausgenommen sind Molke zur Herstellung von alkoholischen Destillaten und Lactit.', '6'), 
(7, 'Sesamsamen', 'Sesam, egal ob im Rohzustand, als Öl oder Paste, in Gebäck, Marinaden, Dressings, Falafel, Müsli, Hummus.', '7'), 
(8, 'Nüsse', 'Mandeln, Haselnüsse, Walnüsse, Kaschunüsse / Cashewnüsse, Pekannüsse, Paranüsse, Pistazien, Macadamia- / Queenslandnüsse sowie sämtliche daraus gewonnenen Erzeugnisse außer Nüssen zur Herstellung von alkoholischen Destillaten.', '8'), 
(9, 'Erdnüsse', 'Alle Erzeugnisse aus Erdnüssen wie Erdnussöl und –Butter; Vorkommen in Gebäck und Kuchen, Desserts, vorfrittierten Produkten wie Pommes Frites oder Rösti, Aufstrichen, Füllungen etc. ', '9'), 
(10, 'Eier', 'Als Flüssigei, Lecithin oder (Ov) Albumin und so, wie es in Mayonnaise, Panaden, Dressings, Kuchen, Suppen, Saucen, Nudeln, Glasuren und natürlich generell bei allen Eier-Speisen vorkommt.', '10'), 
(11, 'Lupinen', 'Lupinensamen, Lupinenmehl, Milch, Tofu und Konzentrat, wie es sich in Brot- und Backwaren, Nudeln, Gewürzen, Würsten, Aufstrichen oder Süßspeisen findet. ', '11'), 
(12, 'Senf', 'Betroffen sind hier sowohl Senfkörner als auch Pulver und alle daraus gewonnenen Erzeugnisse wie Dressings, Marinaden, Currys, Wurstwaren, Aufstriche, Gewürzmischungen etc.', '12'), 
(13, 'Soja', 'Sojabohnen und daraus gewonnene Erzeugnisse, also etwa Miso, Sojasauce, Sojaöl, Gebäck, Marinaden, Kaffeeweißer, Suppen, Saucen, Dressings. Ausgenommen ist vollständig raffiniertes Sojabohnen-Öl und -Fett.', '13'), 
(14, 'Weichtiere', 'Schnecken, Tintenfisch, Austern, Muscheln und alle Erzeugnisse, in denen Weichtiere oder Spuren von ihnen enthalten sind, also Gewürzmischungen, Saucen, asiatische Spezialitäten, Salate oder Pasten.', '14');

-- Testdatensatz (klein)
-- Das Passwort ist für beide Testnutzer "root"
INSERT INTO `findlunchandswa`.`swa_sales_person` (`id`, `password`, `first_name`, `second_name`, `street`, `street_number`, `zip`, `city`, `phone`, `country_code`, `email`, `iban`, `bic`, `salary_percentage`) VALUES (1, "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2", "Max", "Mustermann", "Lothstr", "34", "80335", "München", "089/1234 1234", "DE", "carl@hm.edu", "DE123456789012312312312312", "DEUTDEDBBER", 0.33);
-- Zusätzlicher VerMa ohne Aufgaben oder Restaurants
INSERT INTO `findlunchandswa`.`swa_sales_person` (`id`, `password`, `first_name`, `second_name`, `street`, `street_number`, `zip`, `city`, `phone`, `country_code`, `email`, `iban`, `bic`, `salary_percentage`) VALUES (2, "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2", "Tom", "Chefkoch", "Lothstr", "34", "80335", "München", "089/1234 1234", "DE", "emptyMail@hm.edu", "DE123456789012312312312312", "DEUTDEDBBER", 0.33);

INSERT INTO `findlunchandswa`.`restaurant` (`id`, `customer_id`, `name`, `street`, `street_number`, `zip`, `city`, `country_code`, `location_latitude`, `location_longitude`, `email`, `phone`, `url`, `restaurant_type_id`, `restaurant_uuid`, `qr_uuid`, `swa_offer_modify_permission`, `swa_blocked`, `swa_sales_person_id`) VALUES
(11, 510207137, 'FH München Mensa', 'Münchnerstr', '13', '80335', 'München', 'DE', 48.1534, 48.1534, 'testrestaurant1@gm.com', '089/123123', 'http://www.FHMensa.com', 3, '5b36b3e3-054d-41b9-be3c-19fcece09fb5', _binary 0x89504E470D0A1A0A0000000D49484452000000FA000000FA0100000000A09767900000022A4944415478DAED993D8E83400C858D282839023721178B04522E06379923A49C02E17DCF437643126DB10D4F5A28103F5F819D67FBCDC4FCF763B213388113F8D780E1E87C764FB96F6F9EAD5DF86890021AF7A5C36934AB709B2FF76BE77E170326BB76F876C453DDAF16B7B8520498651C23DE212855C06A66D9CCA75611083DCCDB151E7D16CCB140545633E3F1F7E943E91D0CF8160AA4601085F5EDA72676380015400A7542A35A3A96BFF9DA6A01C86DED06003D1449CF5550AE05E086A554025A2C33D5A606F88ACAC7F5E07887CA9A100B221BA400F4A3E84C106D6FE8F633E4EB6A4009204626D08EB7692B2F1D00BF3DE63ABB3DA711E3F1C49314C0CA4A7837C286300A161A5D931480B46E52C825C11C49D3F3381000F0ED756AA6B664D9D9F2F77AD00098655616926E54069ABF8901E8F175CAF49A90EF12ED89416901AC2CAE2F6038D99922A8BD60040078CD81A1D0B0870D418F6A9E1D8802401B5C66504CCB668568E500A31E2200DEA5C877AEC400FA22B4273824543E16BE5528570CB0D043CC75DF5ABE35CF865301083D2080CD10C784CABD1A806ECFA2A754E9DA61936CA70705000120C16BD8CCD29EEC7529773CF058A9B15B71645ED84DF7ABA4E38158F3C21A4305BC8CCDA2BDE114001EBB0723EA09DD2A360ECCD480D8C2A2A18B1DA328FFF78D1A05C07FF606E1455E16C532C05C4652D8906DAB500AA01EF0ED430A2F021B774B72405456CC4D14FD5256BF7BB327009C7F709CC0099CC05F802F0D4D513FCFB82E620000000049454E44AE426082, 0, 0, 1),
(12, 954164649, 'Cafe Bar Mellow (Lounge Cafe Restaurant)', 'Dachauerstr', '159', '80636', 'München', 'DE', 48.1545, 11.5528, 'testrestaurant2@gm.com', '089/89050923', 'http://www.Mellow.de', 2, '42e559a9-9cab-456a-a809-69f239bbb463', _binary 0x89504E470D0A1A0A0000000D49484452000000FA000000FA0100000000A0976790000002284944415478DAED99418EAB3010441BB1F09223709370B14841CAC5E0263E024B2F22F75495FF9F09C9CC5FFC0D2D0D2C50306FA4D8A92E577BCCFF7D2D7602277002BF1A305C63AAB8D9D446AF1CBA850292FB6374DFCCD2EAF7ECD5AE7C0C062C1C59ACF7D2F93C26DFAEA3751101EB3095B40CDE6611132836DC9DB75C2E2101EA01C3B7DC54B07C2B98838156592B54F079FBAEF48E05BE2E88827A187E34B1038175A33D6D7D96723100654C4F4B1D02A8ACA71513B08B2A0BF24D1E0C309612943BCB4D47A0F8B40603CA84B58554A954EB333C807F34782C801E2F01940EA2C0E3C3767A880060A96FAE7D1DDF5DA27D182C3F16207BC76E74CFF0FD5B4E12C5120C8054FB268AA2F9746FE51F00C002CFDC28E1F1A032DE5D2D1CE04C70C5B4CA3E632822800918462055446350B0A7BC4B201100664DEA15A9B83226F15DAA4F1E150190D1CB48932635E1D3AEFC2300DAD75B7F413D140265F75B040028059812561996DA53C30FB3680067F167F3ACC30C590CDEDC2A14C004D7762379FCA240B2C5020A67D18C0A9B9391CA1E0DD0C141A1DBB79E1C8F160E6078CB12ADF2318B0AF379CE720100B593496E5A5159CA9DED442610F0B74943CC6C0904AB6C97D776F260403DAFA231DEF5ED3021D5608096DA747A5075D88177C95F7F8BA3017D6DF66792AA02494C4046250F60BBB6D7431840276E8A4902D2DB01E6D1804EB4A676CEC19E8D8DE59B471D0CB4CA52565777FE9EE50200E73F384EE0044EE07F800FDA2B6AE4755A414E0000000049454E44AE426082, 0, 0, 1),
(13, 276767662, 'Soon Coffee', 'Lothstraße', '15', '80335', 'München', 'DE', 48.1542, 11.5532, 'testrestaurant3@gm.com', '089/33425235', 'http://www.SoonCoffe.com', 3, '2188dd55-69e2-4fa4-ab58-f7358398e6e7', _binary 0x89504E470D0A1A0A0000000D49484452000000FA000000FA0100000000A0976790000002224944415478DAED993D8E84300C858D28527284DC042E86C4485C6CE6263902650A84F73D9BD50C2BB4C536585A52A0217C456CF9F92723FAFB7ACA0DDCC00DFC6B40B0B2BE96B6E0E521F835726B0A0524D535E3F10005C05E559760C053C62C0D775EAAA54AB7EEAFE1808156E8D26A1D96312890CCCB1BE2005444C0E26113866AA75A1B002701732D60CA4AD0D3FB7122BD8B015BB5C78E0CC8018887E924895D0DB8E81791DA77B30A1EC59C1E0AD8A45569742E088589D9548BF49FAE0E00E0056BB05377AB9815351ED0F9B10BA8355B36E55E28C0D213B6E7C2C8C809A6E43D72E30075E0B11B5D052915CAEABB1F753306B0A003D9AC0D712FAFF2C38A0040B367A662F18A440A796D1FF1100190C60BA530518D826F7075348052477384BA09F9CF9A081CE41F0180F2ED1B3B101A4065217C6301E8D52756F8315B9B94F6D6586301ECE52AE3C14B12EC39CA3F020029B51E14E6EA64C0A7AB23005E2D8525891AABE22B16501B6B4320AA6AD59246A56312BB1E40A21F394ECE5EE1DD8ACF6C1F04D895C56F0C5F0F8F50000AD183F3CF23F3ECC5C6497D060358D22D1E8A5F21E05B39E6870000BD2C267A344C56DC85D36F2C40F7E95CAC6E16AB46E1807DE67510D98A89EA58580300EFDB83EA374678A4F3EB850B01AB9B3DBA760B0AAFF03181EFD6985D53AB310136EC6CE225C386560F83790880F100E5AF9CCE4DFE7629ACA10053965DC1C0D5AD17F7C3A41601B8FFE0B8811BB881BF005F90AE3281AE9564D70000000049454E44AE426082, 0, 0, 1),
(14,'333771658','Burger House','Theresienstraße','37','80789','München','DE', 11.5438, 48.1565,'testrestaurant4@gm.com','089/88770099','www.Burger-House-MUC.de', 1 ,'3f91ad5b-d08f-4e4b-b9cf-d3d8a685bc9d', _binary 0x89504E470D0A1A0A0000000D49484452000000FA000000FA0100000000A0976790000002234944415478DAED99318E83400C458D2828390237818B2181948BC14DE60894534478FFFF93DD1559B4C536585A285098BC8271BEBF3D8EF9EFD76237700337F0AF01C3D5656B1FC92A7F768DFB934B5328802B9DEFA0CC5A002B1F7D0B062C3676B9C777B879CA8647AB420258361B7C36A131010980A270EB4F777135403D30CA00200A3F17CCC58032AB59B7F1FB76927A17035EA876668047CB25CAEF267635B0B430A5DEEA04D13E01F84CA30A0760174A7FEC026E457E08066899BB70B8E904CBDF6AAC0503703D3CF7F4287E67036EB08429148065837D420F8D6FF2FDD9905EB18066FF8C3228845A6EE0C1803C6CA677EF9A1D37C8A34EF920DA00009D3D9132E658921B1CDC3E02A0BA8ECF8C774BF916CA4301CCA284F49F58413DD1AD8CF20805647ABC299F5EC509963F6CB10004B8B8BDDE3DA9B8AB21890494867D61DAA3649A7CB5D9830108F0A3A85446805EEEEDA4160170369C08B0A93546A8C1376B30406135557815A74AC0A11C0400BC2CBF50B6495DAE8201AED8163DC0A394F9C7162500F0E9F6A349191205CE6C1E0AC00690593AFAA88D5B59418FE5E07A80D55CD3032BA1EEB18FE36F1101909BAAE1DCD91AB3829E1C27AF054A87A9AE93EF0ED1BE4D0F2200657AA0152A57F6F45E92AE073887812866369C33A71BC686242060D5977C3947381CCCC30008708DF30FBA62EDA759DA60C06BA2C550AB3F26B57A2CC0CAB08DC7B58A7318A23FD3FF62E0FE83E3066EE006FE027C0011D71E6D9C3BFA520000000049454E44AE426082,'0','0','1');
	
INSERT INTO `findlunchandswa`.`course_types` (`id`, `restaurant_id`, `name`, `sort_by`) VALUES
(1, 11, 'Hauptspeise', 1),
(2, 11, 'Vorspeise', 1),
(3, 11, 'Salate', 1),
(4, 12, 'Hauptspeise', 1),
(5, 12, 'Vorspeise', 1),
(6, 12, 'Nachspeise', 1),
(7, 13, 'Hauptspeise', 1),
(8, 13, 'Desert', 1),
(9, 13, 'Vorspeise', 1),
(10, 13, 'Suppen', 1),
(11, 13, 'Getränke', 1),
(12, 11, 'Vorspeise', 1),
(13,'14','Burger',1),
(14,'14','Salate',1),
(15,'14','Snacks',1);

INSERT INTO `findlunchandswa`.`restaurant_has_kitchen_type` (`restaurant_id`, `kitchen_type_id`) VALUES
(11, 3),
(11, 4),
(12, 1),
(13, 2),
(14, 1);

INSERT INTO `findlunchandswa`.`time_schedule` (`id`, `restaurant_id`, `offer_start_time`, `offer_end_time`, `day_of_week_id`) VALUES
(1, 11, '1970-01-01 11:30:00', '1970-01-01 13:00:00', 1),
(2, 11, '1970-01-01 11:30:00', '1970-01-01 14:00:00', 2),
(3, 11, '1970-01-01 11:30:00', '1970-01-01 15:00:00', 3),
(4, 11, '1970-01-01 11:30:00', '1970-01-01 14:00:00', 4),
(5, 11, '1970-01-01 11:30:00', '1970-01-01 13:00:00', 5),
(6, 11, \N, \N, 6),
(7, 11, \N, \N, 7),
(8, 12, '1970-01-01 11:30:00', '1970-01-01 13:45:00', 1),
(9, 12, '1970-01-01 11:30:00', '1970-01-01 13:45:00', 2),
(10, 12, '1970-01-01 10:15:00', '1970-01-01 13:00:00', 3),
(11, 12, '1970-01-01 10:15:00', '1970-01-01 14:00:00', 4),
(12, 12, '1970-01-01 10:15:00', '1970-01-01 15:00:00', 5),
(13, 12, '1970-01-01 10:15:00', '1970-01-01 14:00:00', 6),
(14, 12, '1970-01-01 10:15:00', '1970-01-01 14:00:00', 7),
(15, 13, '1970-01-01 11:15:00', '1970-01-01 13:00:00', 1),
(16, 13, '1970-01-01 11:15:00', '1970-01-01 14:00:00', 2),
(17, 13, '1970-01-01 11:15:00', '1970-01-01 15:00:00', 3),
(18, 13, '1970-01-01 11:15:00', '1970-01-01 14:00:00', 4),
(19, 13, '1970-01-01 11:15:00', '1970-01-01 14:30:00', 5),
(20, 13, '1970-01-01 11:15:00', '1970-01-01 14:30:00', 6),
(21, 13, '1970-01-01 11:15:00', '1970-01-01 14:30:00', 7),
(22, 14, \N, \N, 1),
(23, 14, \N, \N, 2),
(24, 14, \N, \N, 3),
(25, 14, \N, \N, 4),
(26, 14, \N, \N, 5),
(27, 14, \N, \N, 6),
(28, 14,'1970-01-01 15:00:00', '1970-01-01 22:00:00', 7);

INSERT INTO `findlunchandswa`.`opening_time` (`id`, `opening_time`, `closing_time`, `time_schedule_id`) VALUES
(1, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 1),
(2, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 2),
(3, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 3),
(4, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 4),
(5, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 5),
(6, \N, \N, 6),
(7, \N, \N, 7),	
(8, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 8),	
(9, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 9),
(10, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 10),
(11, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 11),
(12, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 12),
(13, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 13),
(14, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 14),
(15, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 15),
(16, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 16),
(17, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 17),
(18, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 18),
(19, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 19),
(20, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 20),
(21, '1970-01-01 07:00:00', '1970-01-01 22:00:00', 21),
(22, '1970-01-01 10:00:00', '1970-01-01 22:00:00', 22),
(23, '1970-01-01 10:00:00', '1970-01-01 22:00:00', 23),
(24, '1970-01-01 10:00:00', '1970-01-01 22:00:00', 24),
(25, '1970-01-01 10:00:00', '1970-01-01 22:00:00', 25),
(26, '1970-01-01 10:00:00', '1970-01-01 22:00:00', 26),
(27, '1970-01-01 10:00:00', '1970-01-01 23:00:00', 27),
(28, '1970-01-01 10:00:00', '1970-01-01 23:00:00', 28);

INSERT INTO `findlunchandswa`.`offer` (`id`,`swa_change_request_id`, `restaurant_id`, `title`, `description`, `price`, `preparation_time`, `start_date`, `end_date`, `needed_points`, `sold_out`, `course_type`, `swa_change_request`) VALUES
(1, 15, 11,'Champignonreispfanne (Tg1) ', 'vegan', '1.00', 2, '2017-01-08', '2017-08-31', 30, FALSE, 1, 0),
(2, 0, 11, 'Hackbällchen mit Paprikasauce (Tg3)', 'Rindfleisch und Schweinefleisch', '1.90', 4, '2017-01-08', '2017-08-31', 35, FALSE, 1, 0),
(3, 0, 11, 'Pfannkuchen mit Schokosauce (Tg2)', 'fleischlos', '1.59', 3, '2017-01-08', '2017-08-31', 25, FALSE, 2, 0),
(4, 0, 11, 'Putengulasch (Tg4) ', 'Putenfleisch', '2.40', 5, '2017-01-08', '2017-08-31', 37, FALSE, 1, 0),
(5, 0, 12, 'Country Potatoes', 'Knusprige Countrypotatoes mit Sourcreamdip', '4.90', 5, '2017-01-08', '2017-08-31', 40, FALSE, 4, 0),
(6, 0, 12, 'Feurige Bohnen', 'Chili con Carne, der leckere texanische Eintopf)', '4.50', 6, '2017-01-08', '2017-08-31', 38, FALSE, 4, 0),
(7, 0, 12, 'Fleischpflanzerlsemmel', 'Frisch', '2.50', 3, '2017-01-08', '2017-08-31', 23, FALSE, 4, 0),
(8, 0, 12, 'Wiener mit Semmel oder Kartoffelsalat', 'klein', '2.90', 2, '2017-01-08', '2017-08-31', 15, FALSE, 4, 0),
(9, 0, 12, 'Wiener mit Semmel oder Kartoffelsalat ', 'groß', '4.00', 5, '2017-01-08', '2017-08-31', 30, FALSE, 4, 0),
(10, 0, 13, 'Butterbreze', 'mit Salz', '1.10', 1, '2017-01-08', '2017-08-31', 10, FALSE, 4, 0),
(11, 16, 13, 'Früchtetee', 'mit Himbeeren und Erdbeeren', '1.30', 2, '2017-01-08', '2017-08-31', 15, FALSE, 9, 0),
(12, 0, 13, 'Nussschnecke', 'jeden Tag frisch', '1.80', 1, '2017-01-08', '2017-08-31', 20, FALSE, 8, 0),
(13, 0, 13, 'Schwarzer Kaffe', 'aus Brasilien', '1.00', 1, '2017-01-08', '2017-08-31', 8, FALSE, 11, 0),
(15, 0, 11, 'Champignonreispfanne (Tg1) ', 'vegetarisch', '3.00', 2, '2017-01-08', '2017-08-31', 35, FALSE, 3, 1),
(16, 0, 13, 'Früchtetee mit Kräutern', 'Kräuter aus der bayerischen Heimat!', '1.50', 2, '2017-01-08', '2017-08-31', 15, FALSE, 11, 1);

INSERT INTO `findlunchandswa`.`offer_has_day_of_week` (`offer_id`, `day_of_week_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(5, 7),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 7),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 7),
(8, 1),
(8, 2),
(8, 3),
(8, 4),
(8, 5),
(8, 6),
(8, 7),
(9, 1),
(9, 2),
(9, 3),
(9, 4),
(9, 5),
(9, 6),
(9, 7),
(10, 1),
(10, 2),
(10, 3),
(10, 4),
(10, 5),
(10, 6),
(10, 7),
(11, 1),
(11, 2),
(11, 3),
(11, 4),
(11, 5),
(11, 6),
(11, 7),
(12, 1),
(12, 2),
(12, 3),
(12, 4),
(12, 5),
(12, 6),
(12, 7),
(13, 1),
(13, 2),
(13, 3),
(13, 4),
(13, 5),
(13, 6),
(13, 7),
(15, 1),
(15, 2),
(15, 3),
(15, 4),
(15, 5),
(15, 6),
(16, 2),
(16, 3),
(16, 4),
(16, 5),
(16, 6),
(16, 7);

INSERT INTO `findlunchandswa`.`offer_has_additives` (`offer_id`, `additives_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(3, 1),
(3, 3),
(3, 5),
(3, 7),
(3, 9),
(3, 11),
(3, 13),
(4, 2),
(4, 4),
(4, 6),
(4, 8),
(4, 10),
(4, 12),
(4, 14),
(5, 1),
(5, 2),
(5, 3),
(5, 7),
(5, 8),
(5, 9),
(5, 13),
(6, 4),
(6, 5),
(6, 6),
(6, 10),
(6, 11),
(6, 12),
(6, 14),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 7),
(8, 8),
(8, 9),
(8, 10),
(8, 11),
(8, 12),
(8, 13),
(8, 14),
(9, 1),
(9, 2),
(10, 3),
(10, 4),
(11, 5),
(11, 6),
(12, 7),
(13, 1),
(13, 2),
(13, 3),
(13, 4),
(13, 5),
(13, 6),
(13, 7),
(15, 1),
(15, 2),
(15, 3),
(15, 5),
(15, 6),
(15, 7),
(16, 7),
(16, 8);

INSERT INTO `findlunchandswa`.`offer_has_allergenic` (`offer_id`, `allergenic_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(3, 1),
(3, 3),
(3, 5),
(3, 7),
(3, 9),
(3, 11),
(3, 13),
(4, 2),
(4, 4),
(4, 6),
(4, 8),
(4, 10),
(4, 12),
(4, 14),
(5, 1),
(5, 2),
(5, 3),
(5, 7),
(5, 8),
(5, 9),
(5, 13),
(6, 4),
(6, 5),
(6, 6),
(6, 10),
(6, 11),
(6, 12),
(6, 14),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 7),
(8, 8),
(8, 9),
(8, 10),
(8, 11),
(8, 12),
(8, 13),
(8, 14),
(9, 1),
(9, 2),
(10, 3),
(10, 4),
(11, 5),
(11, 6),
(12, 7),
(13, 1),
(13, 2),
(13, 3),
(13, 4),
(13, 5),
(13, 6),
(13, 7),
(15, 1),
(15, 2),
(15, 3),
(15, 5),
(15, 6),
(15, 7),
(16, 9),
(16, 10);

INSERT INTO `findlunchandswa`.`swa_todo_list` (`id`, `todo_request_typ_id`, `sales_person_id`, `restaurant_id`, `offer_id`, `datetime`) VALUES (1, 1, 1, 11, 1, '2017-07-04 11:47:59');
INSERT INTO `findlunchandswa`.`swa_todo_list` (`id`, `todo_request_typ_id`, `sales_person_id`, `restaurant_id`, `offer_id`, `datetime`) VALUES (2, 2, 1, 13, null, '2017-07-04 11:50:02');
INSERT INTO `findlunchandswa`.`swa_todo_list` (`id`, `todo_request_typ_id`, `sales_person_id`, `restaurant_id`, `offer_id`, `datetime`) VALUES (3, 3, 1, 12, null, '2017-07-02 11:50:36');
INSERT INTO `findlunchandswa`.`swa_todo_list` (`id`, `todo_request_typ_id`, `sales_person_id`, `restaurant_id`, `offer_id`, `datetime`) VALUES (5, 3, 1, 14, null, '2017-10-26 21:55:36');
INSERT INTO `findlunchandswa`.`swa_todo_list` (`id`, `todo_request_typ_id`, `sales_person_id`, `restaurant_id`, `offer_id`, `datetime`) VALUES (6, 1, 1, 13, 11, '2017-10-26 22:55:36');

INSERT INTO `findlunchandswa`.`donation_per_month` (`date`, `amount`, `restaurant_id`, `datetime_of_update`) VALUES 
('2017-11-13', 25.5, 11, '2017-11-13 11:11:13'),
('2017-10-13', 35.5, 11, '2017-10-13 11:11:13'),
('2017-09-13', 55.5, 11, '2017-09-13 11:11:13'),
('2017-11-14', 45, 12, '2017-11-15 11:11:13'),
('2017-10-14', 65, 12, '2017-10-15 11:11:13'),
('2017-09-14', 10, 12, '2017-09-15 11:11:13'),
('2017-11-15', 90, 13, '2017-11-16 11:11:13'),
('2017-10-15', 120, 13, '2017-10-16 11:11:13'),
('2017-09-15', 110, 13, '2017-09-16 11:11:13');

INSERT INTO `findlunchandswa`.`reservation` (`reservation_number`, `amount`, `reservation_time`, `confirmed`, `rejected`, `total_price`, `donation`, `used_points`, `user_id`, `offer_id`, `euro_per_point_id`, `restaurant_id`) VALUES 
(1000, 2, '2017-11-13 11:25:12', 1, 0, 15.75, 3.65, 0, 2, 1, 1, 11),
(1001, 1, '2017-10-13 11:25:12', 1, 0, 12.30, 1.20, 0, 2, 2, 1, 11),
(1002, 3, '2017-09-13 11:25:12', 1, 0, 35.13, 2.72, 0, 2, 3, 1, 11),
(1003, 4, '2017-11-13 11:25:12', 1, 0, 37.20, 0.50, 0, 2, 5, 1, 12),
(1004, 1, '2017-10-13 11:25:12', 1, 0, 5.13, 0.33, 0, 2, 6, 1, 12),
(1005, 1, '2017-09-13 11:25:12', 1, 0, 4, 1.78, 0, 2, 7, 1, 12),
(1006, 5, '2017-11-13 11:25:12', 1, 0, 37.20, 0.50, 0, 2, 13, 1, 13),
(1007, 2, '2017-10-13 11:25:12', 1, 0, 5.13, 0.33, 0, 2, 13, 1, 13),
(1008, 1, '2017-09-13 11:25:12', 1, 0, 4, 1.78, 0, 2, 13, 1, 13);
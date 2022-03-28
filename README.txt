U fajlu Database nalazi se kod sql baze podataka koju je potrebno pokrenuti, uz pomoc liquibase ce te tabele biti popunjene
osnovnim podacima(samo u dev modu) prilikom prvog pokretanja. Ostatak podataka u bazi ce se popunjavati testiranjem api servisa.
Servisi koji pocilju sa http://localhost:8080/api/admin/ moze im pristupiti samo korisnik sa rolom ROLE_ADMIN (jwt token).
Prvi admin se dodaje uz pomoc specijalnog kljuca(imate dolje taj api).
Trajanje jwt tokena 60min.


//(svi) Registracija korisnika
(post) - http://localhost:8080/api/authenticate/register
body: UserCreateDTO
{	
    "firstName": "Marko",
    "lastName": "Markovic",
    "username": "MM@gmail.com",
    "password": "12345678"
}
napomena: Radi se validacija podataka
--------------------------------

//Registracija admina
(post) - http://localhost:8080/api/authenticate/register-admin
header:	Authorization adminadmin (adminadmin je sifra za dodavanje prvog admina)	
body: UserCreateDTO 
{	
    "firstName": "admin",
    "lastName": "admin",
    "username": "AA@gmail.com",
    "password": "12345678"
}
napomena: Radi se validacija podataka
-------------------------------

//(svi) Login
(post) - http://localhost:8080/api/authenticate/login
body: UserLoginDTO
{
    "username": "AA@gmail.com",
    "password": "12345678"
}
------------------------------

//(admin) Dodavanje plovila na oglase
(post) - http://localhost:8080/api/admin/boat/add
header: Authorization Bearer {adminov token}
body:
{
    "category": "speed boat",
    "price": "70",
    "brand": "sea doo"
}
-----------------------------

//(admin) Brisanje oglasa
(delete) - http://localhost:8080/api/admin/boat/delete/{id}
header: Authorization Bearer {adminov token}
-----------------------------

//(admin) Dodavanje role
(post) - http://localhost:8080/api/admin/role/add
header: Authorization Bearer {adminov token}
body: RoleDTO
{
    "name": "ROLE_DEVELOPER",
    "description": "Description..." 
}
------------------------------

//(admin) Brisanje role
(delete) - http://localhost:8080/api/admin/role/delete/{id}
header: Authorization Bearer {adminov token}
napomena: U slucaju da se pokusa obrisati rola sa id-jem 1 ili 2(jer su to dvije glavne role koje se koriste za security), 
izaci ce custom exception koji nasledjuje exception(ne run time exception). 
Nece prekinuti program jer imam klasu za obradjuvanje tog exception-a i izaci se custom poruka.
------------------------------

//(admin) Dodavanje role korisniku(admin moze da doda admina)
(get) - http://localhost:8080/api/admin/user/add-role/{userId}
header: Authorization Bearer {adminov token}
	RoleId {id role}
napomena: metoda baca custom exceptipn u slucaju da ne postoji korisnik ili rola sa tim id-jem
-----------------------------

//(admin) Uklanjanje role korisniku
(delete) - http://localhost:8080/api/admin/user/remove-role/{userId}
header: Authorization Bearer {adminov token}
	RoleId {id role}
napomena: metoda baca custom exceptipn u slucaju da ne postoji korisnik ili rola sa tim id-jem
-----------------------------

//(admin) Brisanje korisnika i sve njegove veze sa rolama
(delete) - http://localhost:8080/api/admin/user/delete/{userId}
header: Authorization Bearer {adminov token}
napomena: metoda baca custom exceptipn u slucaju da ne postoji korisnik ili da nije vratio sva plovila koja je iznajmio
-----------------------------

//(admin) Svi korisnici
(get) - http://localhost:8080/api/admin/user/all
header: Authorization Bearer {adminov token}
napomena: Svi password-i koje se mogu koristiti se sifruju prilikom registracije, 
oni koji nijesu sifrovani se ne mogu koristiti(jer se desifruju prije provjere) i tu su samo zbog kolicine podataka
------------------------------

//(admin) Svi korisnici sa odredjenom rolom
(get) - http://localhost:8080/api/admin/user/with-role/{roleId}
header: Authorization Bearer {adminov token}
------------------------------

//(svi prijavljeni) metoda brise korisnika koji je pozvao metodu, i sve njegove veze sa rolama(preko jwt tokena dolazim do korisnika)
(delete) - http://localhost:8080/api/user/delete-yourself
header: Authorization Bearer {token}
napomena: nije moguce izvrsiti brisanje ako nijesu vracena sva iznajmljena plovila
-----------------------------

//(svi prijavljeni) Iznajmljivanje plovila, biramo plovilo i datum do kad ga iznajmljujemo(preko jwt tokena dolazim do korisnika)
(post) - http://localhost:8080/api/boat/renting
header: Authorization Bearer {token}
body:
{
    "boatId": 1,
    "rentingUntil": "2022/10/15",
    "cardNumber": "0000000000000000",
    "CardholderName": "makro Markovic",
    "MM_YY": "05/21",
    "cvv": "123"
}
napomena: podaci o kartici se ne provjeravaju, samo je ostavljeno mjesto da se to naknadno odradi u saradnji sa nekom bankom.
-----------------------------

//(admin) Korisnici koji trenutno iznajmljuju vozilo
(get) - http://localhost:8080/api/admin/user/with-rented-boats
header: Authorization Bearer {adminov token}
-----------------------------

//(admin) Sva plovila koja su trenutno iznajmljena
(get) - http://localhost:8080/api/admin/boat/rented
header: Authorization Bearer {adminov token}
-----------------------------

//(admin) Kad korisnik vrati plovilo, uklanja se datum do kad je izdat iz tabele boat, i veza sa korisnikom koji je iznajmio
(put) - http://localhost:8080/api/admin/user/remove-rented-boat/{userId}/{boatId}
header: Authorization Bearer {adminov token}
napomena: provjereva se postojanje i korisnika i plovila
-----------------------------

//(svi prijavljeni) pretraga po vise opcionih parametara
(get) - http://localhost:8080/api/boat/custom-search
napomena: opcioni parametri su iz klase BoatSearch, u nastavku slijedi par primjera:
http://localhost:8080/api/boat/custom-search?priceGreater=30&priceLess=130
http://localhost:8080/api/boat/custom-search?priceLess=150&categoryContain=jet ski
http://localhost:8080/api/boat/custom-search?brandContain=yam&categoryContain=boat
http://localhost:8080/api/boat/custom-search?freeAfter=2023/10/10
-----------------------------












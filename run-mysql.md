docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=gio --env MYSQL_PASSWORD=gio --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8-oracle


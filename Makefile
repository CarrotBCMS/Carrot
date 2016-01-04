##########################################
### Makefile: Carrot
###
### War deployment
###
### Last Update: 1/4/16
##########################################

GRUNT_BUILD = grunt build --force
GRUNT_CLEAN = grunt clean --force
MVN_PACKAGE = mvn PACKAGE

POM_REF = ./core/pom.xml
POM_COPY_REF = ./core/pom_copy.xml

APP_JS_REF = ./client/app/scripts/app.js
APP_JS_COPY_REF = ./client/app/scripts/app_copy.js

PROPS_REF = ./core/src/main/resources/application.yml
PROPS_COPY_REF = ./core/src/main/resources/application_copy.yml

help:
	echo all - Deploys carrot.x.x.x.war inside target directory.
	echo clean - Cleans all

clean:
	echo Cleaning up ...
	rm -f @$(POM_COPY_REF)
	rm -f @$(APP_JS_COPY_REF)
	rm -f @$(PROPS_COPY_REF)

deploy:
	echo Deploying Carrot ...
	echo '######################'
	echo 1) Backing up files ...
	cp -ru @$(POM_REF) @$(POM_COPY_REF)
	cp -ru @$(APP_JS_REF) @$(APP_JS_COPY_REF)
	cp -ru @$(PROPS_REF) @$(PROPS_COPY_REF)
	echo 2) Modifying files ...
	echo 3) Generating build ...
	cd client && @$(GRUNT_BUILD) && cd..;
	cd core && @$(MVN_PACKAGE) && cd..;
	cd client && @$(GRUNT_CLEAN) && cd..;
	echo 4) Renaming build ...
	find / -iname "*.war" -type f -exec carrot.war

all: clean deploy clean

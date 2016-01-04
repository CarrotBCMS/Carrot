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
POM_COPY_REF = ./core/pom.bak
POM_DEPL_REF = ./core/pom.depl

APP_JS_REF = ./client/app/scripts/app.js
APP_JS_COPY_REF = ./client/app/scripts/app.bak

PROPS_REF = ./core/src/main/resources/application.yml
PROPS_COPY_REF = ./core/src/main/resources/application.bak
PROPS_DEPL_REF = ./core/src/main/resources/application.depl

.PHONY: clean help deploy

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
	cp -ru @$(POM_DEPL_REF) @$(POM_REF)
	cp -ru @$(PROPS_DEPL_REF) @$(PROPS_REF)
	# Replace inplace
	sed -i -e 's/http://localhost:8080//g' @$(APP_JS_REF)
	echo 3) Generating build ...
	cd client && @$(GRUNT_BUILD) && cd..;
	cd core && @$(MVN_PACKAGE) && cd..;
	cd client && @$(GRUNT_CLEAN) && cd..;
	echo 4) Renaming build ...
	find / -iname "*.war" -type f -exec carrot.war
	echo 5) Last steps ...
	cp -ru @$(POM_COPY_REF) @$(POM_REF)
	cp -ru @$(APP_JS_COPY_REF) @$(APP_JS__REF)
	cp -ru @$(PROPS_COPY_REF) @$(PROPS_REF)

all: clean deploy clean

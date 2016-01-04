##########################################
### Makefile: Carrot
###
### War deployment
###
### Last Update: 1/4/16
##########################################

GRUNT_BUILD = grunt build --force
GRUNT_CLEAN = grunt clean --force
MVN_PACKAGE = mvn package
MVN_CLEAN = mvn clean

POM_REF = ./core/pom.xml
POM_COPY_REF = ./core/pom.bak
POM_DEPL_REF = ./core/pom.depl

APP_JS_REF = ./client/app/scripts/app.js
APP_JS_COPY_REF = ./client/app/scripts/app.bak

PROPS_REF = ./core/src/main/resources/application.yml
PROPS_COPY_REF = ./core/src/main/resources/application.bak
PROPS_DEPL_REF = ./core/src/main/resources/application.depl

APP_DEV_LINK = http://localhost:8080/
APP_JS_PROD_SYMBOL = prod
APP_JS_DEV_SYMBOL = dev

.PHONY: clean help deploy build_clean

help:
	@echo ''
	@echo '> all - Deploys war inside target directory.'
	@echo '> clean - Cleans all'
	@echo '> build_clean - Cleans build targets'

build_clean:
	@cd client && $(GRUNT_CLEAN) && cd ..;
	@cd core && $(MVN_CLEAN) && cd ..;
	@echo 'All done!'
	@echo '---'

clean:
	@echo Cleaning up ...
	@echo 'All done!'
	@echo '---'
	@rm -f $(POM_COPY_REF)
	@rm -f $(APP_JS_COPY_REF)
	@rm -f $(PROPS_COPY_REF)

deploy:
	@echo '######################'
	@echo '######################'
	@echo 'Deploying Carrot ...'
	@echo '######################'
	@echo '######################'
	@echo '1) Backing up files ...'
	@\cp -r $(POM_REF) $(POM_COPY_REF)
	@\cp -r $(APP_JS_REF) $(APP_JS_COPY_REF)
	@\cp -r $(PROPS_REF) $(PROPS_COPY_REF)
	@echo '2) Modifying files ...'
	@\cp -r $(POM_DEPL_REF) $(POM_REF)
	@sed -i '' -e 's#$(APP_JS_DEV_SYMBOL)#$(APP_JS_PROD_SYMBOL)#g' $(PROPS_REF)
	@sed -i '' -e 's#$(APP_DEV_LINK)##g' $(APP_JS_REF)
	@echo '3) Generating build ...'
	@cd client && $(GRUNT_BUILD) && cd ..;
	@cd core && $(MVN_PACKAGE) && cd ..;
	@cd client && $(GRUNT_CLEAN) && cd ..;
	@echo '4) Last steps ...'
	@\cp -r $(POM_COPY_REF) $(POM_REF)
	@\cp -r $(APP_JS_COPY_REF) $(APP_JS_REF)
	@\cp -r $(PROPS_COPY_REF) $(PROPS_REF)
	@echo 'All done!'
	@echo '---'

all: deploy clean

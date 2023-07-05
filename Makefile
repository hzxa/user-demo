USER_DEMO_REGISTER = user-demo-register
USER_DEMO_ADMIN = user-demo-admin
USER_DEMO_NGINX = user-demo-nginx

VERSION := 1.0.0
BUILD_TIME := $(shell date +%Y%m%d%H%M%S)
#BUILD_COMMIT := $(shell git rev-parse --short HEAD)
BUILD_COMMIT := test
BUILD_VERSION := $(BUILD_TIME).$(BUILD_COMMIT)

# GITTAG = $(shell git describe --tags --abbrev=0)
GITTAG = $(VERSION)-$(BUILD_VERSION)

.PHONY : clean package build push deploy

.DEFAULT_GOAL : help

help:  ## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

gitpull:
	git pull
clean: ## maven clean
	mvn clean

package: ## maven package
	mvn package

ui:
	cd ./user-demo-admin-ui; npm run build
	cd ./user-demo-register-ui; npm run build

images:  ## docker build
	docker build --no-cache --rm -f user-register-dockerfile -t $(USER_DEMO_REGISTER):$(GITTAG) .
	docker tag $(USER_DEMO_REGISTER):$(GITTAG) $(USER_DEMO_REGISTER):latest
	docker build --no-cache --rm -f user-admin-dockerfile -t $(USER_DEMO_ADMIN):$(GITTAG) .
	docker tag $(USER_DEMO_ADMIN):$(GITTAG) $(USER_DEMO_ADMIN):latest
	docker build --no-cache --rm -f user-nginx-dockerfile -t $(USER_DEMO_NGINX):$(GITTAG) .
	docker tag $(USER_DEMO_NGINX):$(GITTAG) $(USER_DEMO_NGINX):latest

build: clean package images

local-run:
	docker-compose up -d

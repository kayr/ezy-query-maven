clean:
	cd ezy-query-mvn-itest && make clean
	cd ezy-query-mvn-plugin && make clean

ci:
	cd ezy-query-mvn-plugin && make ci

test: ci
	cd ezy-query-mvn-itest && make test

publish: test
	cd ezy-query-mvn-plugin && make deploy


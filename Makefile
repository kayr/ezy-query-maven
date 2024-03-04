clean:
	cd ezy-query-mvn-itest && make clean
	cd ezy-query-mvn-plugin && make clean

clean-install:
	cd ezy-query-mvn-plugin && make clean-install

test: clean-install
	cd ezy-query-mvn-itest && make test

publish: test
	cd ezy-query-mvn-plugin && lein deploy


pluginTest:
	cd /home/kayr/var/code/rsrc/clojure/ezy-query-mvn-itest && \
	mvn clean ezy-query-mvn-plugin:ezy-query-mvn-plugin:ezyBuild -e

quick:
	lein install
	cd /home/kayr/var/code/rsrc/clojure/ezy-query-mvn-itest && \
	mvn ezy-query-mvn-plugin:ezy-query-mvn-plugin:ezyInit -e && \
	mvn ezy-query-mvn-plugin:ezy-query-mvn-plugin:ezyBuild -e


clean:
	cd ezy-query-mvn-itest && make clean
	cd ezy-query-mvn-plugin && make clean

ci:
	cd ezy-query-mvn-plugin && make ci

test:
	cd ezy-query-mvn-itest && make test
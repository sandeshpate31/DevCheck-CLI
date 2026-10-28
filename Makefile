.PHONY: test lint complexity cloc java-test

test:
	python -m unittest discover -s python/tests -v

lint:
	python -m flake8 python/devcheck_cli python/devcheck_libs python/tests

complexity:
	python -m radon cc python/devcheck_cli python/devcheck_libs -s -a
	python -m radon mi python/devcheck_cli python/devcheck_libs -s

cloc:
	cloc --exclude-dir=.git,.venv,build,dist,java/build .

java-test:
	bash tests/java-smoke.sh

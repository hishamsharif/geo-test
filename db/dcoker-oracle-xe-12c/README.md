# Oracle Database on Docker

Official Oracle Docker build files to facilitate installation, configuration, and environment setup for DevOps users.

## How to setup and run

Step 0 (pre-requesite) - Ensure you have docker installed (along with docker-compose) with the latest version

Step 1 (pre-requesite) - Before you build the image make sure that you have provided the installation binaries and put them into the right folder.

**IMPORTANT:** You will have to provide the installation binaries of Oracle Database and put them into the `dockerfiles/<version>` folder. You only need to provide the binaries for the edition you are going to install. The binaries can be downloaded from the [Oracle Technology Network](http://www.oracle.com/technetwork/database/enterprise-edition/downloads/index.html), make sure you use the linux link: _Linux x86-64_. The needed file is named _linuxx64\_<version>\_database.zip_. You also have to make sure to have internet connectivity for yum. Note that you must not uncompress the binaries. The script will handle that for you and fail if you uncompress them manually!

Step 2 - from the terminal, execute the "./setup.sh" script found in the root of this docker setup. This will create the database in the docker container, and implicitly calls the "./start.sh" script to start the database and execute initial sql scripts provided

Step 3 (Optional) - from the terminal, execute the "./start.sh" script found in the root of this docker setup. This will start the database and execute initial sql scripts provided inside "./scripts" folder

From SQL clients (i.e. Oracle SQL Developer) :
sqlplus sys/Oracle18@//localhost:1521/XE as sysdba
sqlplus HISHAM/123@//localhost:1521/XE

    sqlplus system/<your password>@//localhost:1521/<your SID>
    sqlplus pdbadmin/<your password>@//localhost:1521/<Your PDB name>

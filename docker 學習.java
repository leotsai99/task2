

// port 指定
(-P 隨機port)
docker run -P -d larrycai/gerrit:review
(-p 指定port)
docker run -p 8888:8080 -p 9999:29418-d larrycai/gerrit:review


// apache
/etc/init.d/apache2 stop
sudo service apache2 stop

// install wget in ubuntu14.04 docker
apt-get update && apt-get dist-upgrade
apt-get install wget

// Remove all Running containers
sudo docker rm $(sudo docker stop -t=1 $(sudo docker ps -a -q))
libo
// Remove all stopped containers
docker rm $(docker ps -a -q)

// install java
"   apt-get install -y openjdk-7-jre && rm -rf /var/lib/apt/lists/*      "

// Remove all container created from a certain image:
docker rm  $(docker ps -a | awk '/myimage:mytag/{print $1}') 

// remove images, 先把container 停止 
sudo docker ps -a | grep Exit | awk '{print $1}' | sudo xargs docker rm

// remove all untagged images
sudo docker images --no-trunc | grep none | awk '{print $3}' | sudo xargs docker rmi

// 檢查目前有那些container正在執行
sudo docker ps -a

// commit
sudo docker commit -m="Added wget" -a="Leo Tsai" c3c myubuntu:14.04

// share folder
sudo docker run -t -i -v /home/leo/docker/share_folder:/home/workplace my:14.04 bash

// build Dockerfile

sudo docker build -t="apache2" /home/leo/docker/apache2
sudo docker build -t="php2" /home/leo/docker/config2

// start apache2 
sudo docker run -d -P apache2
sudo docker run -d -p 8080:80 -p 2022:22 -v /home/leo/docker/share_folder:/var/www -e SERVER_PASSWORD=ubuntu apache2
sudo docker run -d -p 80:80 php2

// ssh 進去用bash
ssh root@127.0.01 -p 2022    (pass: ubuntu)

// docker ssh server in ubuntu
https://docs.docker.com/examples/running_ssh_service/


// WORKDIR
FROM ubuntu:12.10
RUN cd nginx-1.4.1 && ./configure --add-module=../nginx_accept_language_module-master --with-http_ssl_module --with-pcre=/lib/x86_64-linux-gnu --with-openssl=/usr/lib/x86_64-linux-gnu
 =
FROM ubuntu:12.10
WORKDIR nginx-1.4.1
RUN ./configure --add-module=../nginx_accept_language_module-master --with-http_ssl_module --with-pcre=/lib/x86_64-linux-gnu --with-openssl=/usr/lib/x86_64-linux-gnu

// CMD and ENTRYPOINT
I understand that ENTRYPOINT is the binary that is being executed. You can overide entrypoint by -entrypoint="".
docker run -t -i -entrypoint="/bin/bash" ubuntu
CMD is the default argument to container. Without entrypoint, default argument is command that is executed. With entrypoint, cmd is passed to entrypoint as argument. You can emulate a command with entrypoint.

# no entrypoint
docker run ubuntu /bin/cat /etc/passwd

# entry point, emulating cat command
docker run -entrypoint="/bin/cat" ubuntu /etc/passwd

So, main advantage is that with entrypoint you can pass arguments (cmd) to your container. To accomplish this, you need to use both:

# Dockerfile
FROM ubuntu
ENTRYPOINT ["/bin/cat"]

and

docker build -t=cat .

then you can use:

docker run cat /etc/passwd
#              ^^^^^^^^^^^
#                   CMD
#          ^^^      
#          image (tag)- using the default ENTRYPOINT

// get ubuntu image
docker pull ubuntu:14.04
sudo docker run -t -i ubuntu:14.04 /bin/bash









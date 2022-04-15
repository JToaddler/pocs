ECR Docker repository: 
public.ecr.aws/XXXXXX/world-demo-api:0.0.1-SNAPSHOT

ECR Get Password token:
aws ecr get-login-password --region ca-central-1 

Run a container with port forwarding: 
docker run -p 8080:8080 world-demo-api/world-demo-api

Pull docker image from Public ECR 
docker pull public.ecr.aws/XXXXXX/world-demo-api:0.0.1-SNAPSHOT

Run Docker image
sudo docker run -p 8080:8080 public.ecr.aws/XXXXXX/world-demo-api:0.0.1-SNAPSHOT


Start Docker with port forwarding:
docker run -p 8080:8080 public.ecr.aws/XXXXXX/world-demo-api:0.0.1-SNAPSHOT

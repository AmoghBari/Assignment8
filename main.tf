module "ec2" {
    source = "./modules/ec2"
    region = "ap-south-1"
    profile = "default"
    ami = "ami-03bfb2267ba3aa170"
    instance_type = "t3.micro"
    public_subnet_id = module.network.public_subnet_id
    private_subnet_id = module.network.private_subnet_id
    security_group_id = [module.network.security_group_id]
    key_name = module.ssh.key_name
    private_key_pem = module.ssh.key_value
    key_pair = module.ssh.key_pair
}

module "network" {
    source = "./modules/network"
    cidr_block = "10.0.0.0/16"
    public_subnet_cidr = "10.0.1.0/24"
    private_subnet_cidr = "10.0.2.0/24"
  
}

module "ssh" {
    source = "./modules/ssh"
    key_name = "ec2-key"
  
}
variable "region" {}
variable "profile" {}
variable "ami" {}
variable "instance_type" {}
variable "public_subnet_id" {}
variable "private_subnet_id" {}
variable "security_group_id" {
    type = list(string)
    default = []
}
variable "key_name" {}
variable "private_key_pem" {}
variable "key_pair" {}

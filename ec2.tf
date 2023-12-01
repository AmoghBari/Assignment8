provider "aws" {
  region  = "ap-south-1"
  #profile = "default"
}

resource "aws_instance" "public_instance" {
    ami                         ="ami-03bfb2267ba3aa170"
    instance_type               = "t3.micro"
    key_name                    = aws_key_pair.ec2_key_pair.key_name
    subnet_id                   = aws_subnet.public_subnet.id
    vpc_security_group_ids      = [aws_security_group.public_sg.id]
    associate_public_ip_address = true

    tags = {
        Name = "Public Instance"
    }

    provisioner "remote-exec" {
    inline = [
      "sudo yum update -y",
      "sudo yum install -y nginx",
      "sudo service nginx start",
      "sudo chkconfig nginx on",
      "echo '${tls_private_key.ssh_key.private_key_pem}' > /home/ec2-user/private_key.pem",
      "chmod 400 /home/ec2-user/private_key.pem"

    ]

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = tls_private_key.ssh_key.private_key_pem
      host        = self.public_ip
    }
  }
}

resource "aws_instance" "private_instance" {
    ami                    = "ami-03bfb2267ba3aa170"
    instance_type          = "t3.micro"
    key_name               = aws_key_pair.ec2_key_pair.key_name
    subnet_id              = aws_subnet.private_subnet.id
    vpc_security_group_ids = [aws_security_group.public_sg.id]

    tags = {
        Name = "Private Instance"
  }
  user_data = file("user.sh")
}

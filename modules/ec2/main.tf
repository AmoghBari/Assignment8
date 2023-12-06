provider "aws" {
  region  = var.region
}

resource "aws_instance" "public_instance" {
  ami                    = var.ami
  instance_type          = var.instance_type
  subnet_id              = var.public_subnet_id
  vpc_security_group_ids = var.security_group_id
  key_name               = var.key_name
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
      "echo '${var.private_key_pem}' > /home/ec2-user/private_key.pem",
      "chmod 400 /home/ec2-user/private_key.pem"

    ]

    connection {
      type        = "ssh"
      user        = "ec2-user"
      private_key = var.private_key_pem
      host        = self.public_ip
    }
  }
    provisioner "local-exec" {
      command = <<EOT
        echo '${var.private_key_pem}' > private_key.pem
      EOT
  }
}

resource "aws_instance" "private_instance" {
  ami                    = var.ami
  instance_type          = var.instance_type
  subnet_id              = var.private_subnet_id
  vpc_security_group_ids = var.security_group_id
  key_name               = var.key_name

  tags = {

    Name = "Private Instance"
  }

  user_data = file("${path.module}/user.sh")
}

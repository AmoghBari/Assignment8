output "public_ip" {
    value = aws_instance.public_instance.id
}

output "private_ip" {
    value = aws_instance.private_instance.id
}
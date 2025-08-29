# gRPC with Protobuf

## gRPC Proto Types

| Java Type | Proto Type |
|-----------|------------|
| String    | string     |
| int       | int32      |
| long      | int64      |
| float     | float      |
| double    | double     |
| boolean   | bool       |
| byte[]    | bytes      |
| enum      | enum       |
| List      | repeated   |
| Map       | map        |
| Object    | message    |


# Services

A gRPC service is defined using the `service` keyword in a `.proto` file. Each service can have multiple methods which define the RPC calls that can be made.
```proto
syntax = "proto3";

service Greeter {
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

message HelloRequest {
  string name = 1;
}

message HelloReply {
  string message = 1;
}
```

package com.kapil.todo

class Note {
    var nodeId:Int?=null
    var nodeName:String?=null
    var nodeDes:String?=null
    constructor(nodeId:Int,nodeName:String,nodeDes:String)
    {
        this.nodeId=nodeId
        this.nodeName=nodeName
        this.nodeDes=nodeDes
    }

}
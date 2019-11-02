#!/bin/bash

function query() {

	java ${@:2} -Dquery=$1 -DLOG_FILE=query$1.txt -cp 'client/target/tpe2-client-1.0-SNAPSHOT/lib/jars/*' "ar.edu.itba.pod.client.Client"
}

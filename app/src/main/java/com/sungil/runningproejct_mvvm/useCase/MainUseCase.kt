package com.sungil.runningproejct_mvvm.useCase

import com.google.firebase.database.DataSnapshot
import com.sungil.runningproejct_mvvm.dataObject.PostData
import javax.inject.Inject

class MainUseCase @Inject constructor() {

    fun getFollowerData(snapshot : DataSnapshot , follower : ArrayList<String>): ArrayList<PostData> {
        val followerMap = snapshot.value as? HashMap<String ,HashMap<String ,Any>>
        val followerPost = ArrayList<PostData>()
        followerMap?.forEach { (email, innerMap) ->
            if (follower.contains(email)) {
                // innerMap에서 필요한 데이터 추출
                val runningDistance = innerMap["running"]?.toString()?.trim() ?: ""
                val runningTime = innerMap["time"] as? Long ?: 0L
                val title = innerMap["title"] as? String ?: ""
                val content = innerMap["content"] as? String ?: ""
                val nickname = innerMap["nickName"] as? String ?: ""
                // PostData 객체 생성
                val postData = PostData(email , title, content, runningTime, runningDistance , nickname)
                followerPost.add(postData)
            }
        }
        return followerPost

    }

    fun getUnFollowerData(snapshot : DataSnapshot , follower : ArrayList<String>): ArrayList<PostData> {
        val unFollowerMap = snapshot.value as? HashMap<String, HashMap<String, Any>>
        val unFollowerPost = ArrayList<PostData>()
        unFollowerMap?.forEach { (email, innerMap) ->
            if(!follower.contains(email)){
                // innerMap에서 필요한 데이터 추출
                var runningDistance = innerMap["running "]?.toString()?.trim() ?: ""
                if(runningDistance == ""){
                    runningDistance =  innerMap["running"]?.toString()?.trim() ?: ""
                }
                val runningTime = innerMap["time"] as? Long ?: 0L
                val title = innerMap["title"] as? String ?: ""
                val content = innerMap["content"] as? String ?: ""
                val nickname = innerMap["nickName"] as? String ?: ""
                // PostData 객체 생성
                val postData = PostData(email ,title, content, runningTime, runningDistance,nickname)

                unFollowerPost.add(postData)
            }
        }
        return unFollowerPost
    }
}
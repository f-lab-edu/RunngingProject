package com.sungil.runningproejct_mvvm.repository.mainRepositoryImpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sungil.runningproejct_mvvm.appDatabase.RunningDao
import com.sungil.runningproejct_mvvm.appDatabase.UserInfoDao
import com.sungil.runningproejct_mvvm.dataObject.PostData
import com.sungil.runningproejct_mvvm.dataObject.UserInfoDBM
import com.sungil.runningproejct_mvvm.dataObject.WearRunDataDBM
import com.sungil.runningproejct_mvvm.repository.MainRepository
import com.sungil.runningproejct_mvvm.utill.Define
import com.sungil.runningproejct_mvvm.useCase.MainUseCase
import timber.log.Timber
import javax.inject.Inject

//MainRepo hilt  적용
class MainRepositoryImpl @Inject constructor(private val wearRoomData: RunningDao , private val userDao : UserInfoDao , private val useCase : MainUseCase) : MainRepository {
    private val database = Firebase.database(Define.FIREBASE_BASE_URL)
    private var followerLiveData = MutableLiveData<List<String>>()
    private var followerPostLiveData = MutableLiveData<ArrayList<PostData>>()
    private var unFollowerPostLiveData = MutableLiveData<ArrayList<PostData>>()
    private var setFollowerLiveData = MutableLiveData<String>()
    override fun getRunningRoomDB(): LiveData<WearRunDataDBM?> {
        return wearRoomData.getRunningData()
    }

    override fun getFollower(userId : String) {
        val followerUrl = Define.FIREBASE_FOLLOWER +"/"+userId
        database.getReference(followerUrl).addListenerForSingleValueEvent(object  :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value as? HashMap<String, String>
                val followerList = value?.values?.toList() ?: emptyList()
                followerLiveData.value = followerList
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("ERROR to get Follower $error")
            }
        })
    }

    override fun getUserInfo(): UserInfoDBM? {
        return userDao.getUserInfo() ?: return null
    }

    override fun getFollowerPost(follower: ArrayList<String>) {
        val followerUrl = Define.FIREBASE_SNS
        database.getReference(followerUrl).addValueEventListener(object  :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val followerPost = useCase.getFollowerData(snapshot , follower)
                followerPostLiveData.value = followerPost
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("Error to get FollowerPost :$error")
            }
        })
    }

    override fun getUnFollowerPost(follower: ArrayList<String>) {
        val followerUrl = Define.FIREBASE_SNS
        database.getReference(followerUrl).addValueEventListener(object  :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val unFollowerPost = useCase.getUnFollowerData(snapshot , follower)
                unFollowerPostLiveData.value = unFollowerPost
            }
            override fun onCancelled(error: DatabaseError) {
                Timber.e("Error to get FollowerPost :$error")
            }
        })
    }
    override fun setNewFollower(userId: String ,  myId : String) {
        val followerUrl = Define.FIREBASE_FOLLOWER+"/"+myId+"/"+userId
        val followerRef = database.getReference(followerUrl)

        followerRef.setValue(userId){ databaseError, databaseReference ->
            if(databaseError == null){
                Timber.d("Success to set Follower")
                setFollowerLiveData.value = userId
                return@setValue
            }
            setFollowerLiveData.value = ""
            Timber.e("Error to set Follower")
        }

    }

    override fun getFollowerLiveData(): LiveData<List<String>>  = followerLiveData

    override fun getFollowerPostLiveData(): LiveData<ArrayList<PostData>>  = followerPostLiveData
    override fun getUnFollowerPostLiveData(): LiveData<ArrayList<PostData>>  = unFollowerPostLiveData
    override fun getSetFollowerLiveData(): LiveData<String>  = setFollowerLiveData
}
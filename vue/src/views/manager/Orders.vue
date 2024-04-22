<template>
  <div>
    <div class="search" style="margin-bottom: 20px">
      <el-input placeholder="Please enter the order number" style="width: 240px" v-model="orderId"></el-input>
      <el-button type="info" style="margin-left: 10px"  icon="el-icon-search" @click="load(1)">Search</el-button>
      <el-button type="warning"   style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}" >
        <el-table-column prop="id" label="id" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="img" label="goodsimage" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px" v-if="scope.row.goodsImg"
                        :src="scope.row.goodsImg" :preview-src-list="[scope.row.goodsImg]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="goodsName" label="goodsName" show-overflow-tooltip></el-table-column>
        <el-table-column prop="buyName" label="name" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orderId" label="orderId"></el-table-column>
        <el-table-column prop="time" label="orderTime"></el-table-column>
        <el-table-column prop="username" label="recipient"></el-table-column>
        <el-table-column prop="phone" label="contactPhone"></el-table-column>
        <el-table-column prop="address" label="address"></el-table-column>
        <el-table-column prop="num" label="number"></el-table-column>
        <el-table-column prop="price" label="price"></el-table-column>
        <el-table-column prop="status" label="status">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <div :style="getColor(scope.row.status)">{{scope.row.status}}</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="operation" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="changeStatus(scope.row, 'WaitingReceipt')" size="mini" v-if="scope.row.status === 'WaitingDelivery'">Delivery</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Orders",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page number
      pageSize: 10,  // Number of items per page
      total: 0,
      orderId: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    // Get color
    getColor(msg){
      if (msg === "WaitingReceipt"){
        return { color: '#3399FF' };
      }
      if (msg === "WaitingShipment"){
        return { color: '#FFD333' };
      }
    },
    changeStatus(row, status) {
      let data = JSON.parse(JSON.stringify(row))
      data.status = status
      this.$request.put('/orders/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('OperationSuccessful')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    load(pageNum) {  // Page query
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/orders/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderId: this.orderId,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.orderId = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>

<template>
  <div class="drop-board">
    <template v-for="(status, idx) in bookStatusList">
      <card
        :key="idx"
        :book-status="status"
        :book-list="bookListSync"
        @startDrag="startDrag"
        @onDrop="onDrop"
      />
    </template>
  </div>
</template>

<script lang="ts">
  import { Vue, Component, PropSync, Prop } from 'vue-property-decorator';
  import CardItem from '@/components/dragAndDrop/CardItem.vue';
  import Card from '@/components/dragAndDrop/Card.vue';

  export type BookReadingStatusType = 'BOOKING' | 'READING' | 'COMPLETE';
  export type BookExposureStatusType = 'PUBLIC' | 'PRIVATE';
  export type DataTransferType = { dataTransfer: DataTransfer };

  export type Book = {
    id: number;
    accountId: number;
    isbn: string;
    access: BookExposureStatusType;
    status: BookReadingStatusType;
    review: string;
    category: string; // categoryName
    score: number;
    reviewed: boolean;
    // _links: {
    //   self: {
    //     href: 'http://localhost:9000/api/library/books/3';
    //   };
    //   'accountBook-changeAccess': {
    //     href: 'http://localhost:9000/api/library/books/3/access';
    //   };
    //   'accountBook-changeStatus': {
    //     href: 'http://localhost:9000/api/library/books/3/status';
    //   };
    // };
  };

  @Component({
    components: { Card, CardItem },
  })
  export default class Board extends Vue {
    @PropSync('bookList')
    private bookListSync!: Book[];
    @Prop()
    private readonly bookStatus!: BookReadingStatusType;
    private bookStatusList = ['BOOKING', 'READING', 'COMPLETE'];
    private startDrag({
      book,
      dataTransfer,
    }: {
      book: Book;
      dataTransfer: DataTransfer;
    }) {
      dataTransfer.dropEffect = 'move';
      dataTransfer.effectAllowed = 'move';
      dataTransfer.setData('id', book.id.toString());
    }

    private onDrop({
      bookStatus,
      dataTransfer,
    }: {
      bookStatus: BookReadingStatusType;
      dataTransfer: DataTransfer;
    }) {
      const id = Number(dataTransfer.getData('id'));
      const book = this.bookListSync.find(book => book.id === id);
      if (book !== undefined) {
        book.status = bookStatus;
      }
    }
  }
</script>

<style scoped>
  .drop-board {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding: 10px;
    margin: 20px;
    border: 2px solid black;
    text-align: center;
  }
  .drag-card {
    background-color: #e7e1c0;
    margin: 10px;
  }
</style>

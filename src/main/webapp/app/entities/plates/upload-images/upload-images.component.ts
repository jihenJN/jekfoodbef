import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadFilesService } from '../service/upload-files.service';

@Component({
  selector: 'jhi-upload-images',
  templateUrl: './upload-images.component.html',
  styleUrls: ['./upload-images.component.scss'],
})
export class UploadImagesComponent implements OnInit {
  selectedFiles: FileList | undefined = undefined;
  progressInfos: { value: number; fileName: any; percentage?: number }[] = [];
  message = '';
  fileInfos: Observable<any> | undefined = undefined;

  constructor(private uploadService: UploadFilesService) {}

  ngOnInit() {
    this.fileInfos = this.uploadService.getFiles();
  }

  selectFiles(event: Event) {
    this.progressInfos = [];

    const files = (event.target as HTMLInputElement).files;
    if (!files) {
      return;
    }

    let isImage = true;

    for (let i = 0; i < files.length; i++) {
      if (files.item(i)?.type.match('image.*')) {
        continue;
      } else {
        isImage = false;
        alert('Invalid format!');
        break;
      }
    }

    if (isImage) {
      this.selectedFiles = files;
    } else {
      this.selectedFiles = undefined;
      // Since the 'percentage' property is optional, you can set it to undefined instead of null.
      // this.progressInfos[i].percentage = null;
    }
  }

  upload(idx: number, file: File) {
    this.progressInfos[idx] = { value: 0, fileName: file.name };

    this.uploadService.upload(file).subscribe(
      event => {
        if (event && event.type === HttpEventType.UploadProgress) {
          const total = event.total || 1; // Use 1 as a fallback to avoid division by zero
          this.progressInfos[idx].percentage = Math.round(100 * (event.loaded / total));
        } else if (event instanceof HttpResponse) {
          this.fileInfos = this.uploadService.getFiles();
        }
      },
      err => {
        this.progressInfos[idx].percentage = undefined; // Change null to undefined
        this.message = 'Could not upload the file: ' + file.name;
      }
    );
  }

  uploadFiles() {
    this.message = '';
    if (this.selectedFiles) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
      }
    }
  }
}

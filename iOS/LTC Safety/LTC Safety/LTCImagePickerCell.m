//
//  LTCImagePickerCell.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-14.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCImagePickerCell.h"
#import "LTCImageContainer.h"

@interface LTCImagePickerCell () <UIImagePickerControllerDelegate, UINavigationControllerDelegate, LTCImageContainerDelegate>
@property (nonatomic, strong) UIAlertController *alertController;
@property (nonatomic, strong) UIImagePickerController *imagePickerController;
@property (nonatomic, strong) LTCImageContainer *imageContainer;
@end

@implementation LTCImagePickerCell

- (void)setRowDescriptor:(XLFormRowDescriptor *)rowDescriptor {
    [super setRowDescriptor:rowDescriptor];
    self.rowDescriptor.value = self.imageContainer;
}

- (void)configure {
    
    [super configure];
        
    [self setSelectionStyle:UITableViewCellSelectionStyleNone];

    self.imageContainer = [[LTCImageContainer alloc] init];
    self.imageContainer.delegate = self;
    [self.contentView addSubview:self.imageContainer];
    
    NSDictionary *views = @{@"row": self.imageContainer};
    [self.contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[row]-0-|" options:0 metrics:0 views:views]];
    [self.contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-0-[row]-0-|" options:0 metrics:0 views:views]];
}

- (void)_openImage:(UIImagePickerControllerSourceType)source {
    self.imagePickerController = [[UIImagePickerController alloc] init];
    self.imagePickerController.delegate = self;
    self.imagePickerController.allowsEditing = YES;
    self.imagePickerController.sourceType = source;
    
    [self.formViewController presentViewController: self.imagePickerController
                                          animated: YES
                                        completion: nil];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> *)info {
    
    UIImage *image = info[UIImagePickerControllerOriginalImage];
    [self.imageContainer addImage:image];
    
    [self.formViewController dismissViewControllerAnimated: YES completion: nil];
}

- (void)container:(LTCImageContainer *)container heightDidChange:(CGFloat)height {
    self.rowDescriptor.height = height;
    [self.formViewController reloadFormRow:self.rowDescriptor];
}

- (void)containerWillAddImage:(LTCImageContainer *)container {
    self.alertController = [UIAlertController alertControllerWithTitle: self.rowDescriptor.title
                                                               message: nil
                                                        preferredStyle: UIAlertControllerStyleActionSheet];
    
    [self.alertController addAction:[UIAlertAction actionWithTitle: NSLocalizedString(@"Choose From Library", nil)
                                                             style: UIAlertActionStyleDefault
                                                           handler: ^(UIAlertAction * _Nonnull action) {
                                                               [self _openImage:UIImagePickerControllerSourceTypePhotoLibrary];
                                                           }]];
    
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        [self.alertController addAction:[UIAlertAction actionWithTitle: NSLocalizedString(@"Take Photo", nil)
                                                                 style: UIAlertActionStyleDefault
                                                               handler: ^(UIAlertAction * _Nonnull action) {
                                                                   [self _openImage:UIImagePickerControllerSourceTypeCamera];
                                                               }]];
    }
    
    [self.alertController addAction:[UIAlertAction actionWithTitle: NSLocalizedString(@"Cancel", nil)
                                                             style: UIAlertActionStyleCancel
                                                           handler: nil]];
    
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.alertController.modalPresentationStyle = UIModalPresentationPopover;
        self.alertController.popoverPresentationController.sourceView = self.contentView;
        self.alertController.popoverPresentationController.sourceRect = [self.imageContainer sourceRectForImageIndex:0 sourceView:self.contentView];
    }
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.formViewController presentViewController:self.alertController animated: true completion: nil];
    });
}

- (void)container:(LTCImageContainer *)container willRemoveImageAtIndex:(NSInteger)index {
    self.alertController = [UIAlertController alertControllerWithTitle:nil
                                                               message:nil
                                                        preferredStyle:UIAlertControllerStyleActionSheet];
    
    [self.alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"Remove Photo", nil)
                                                             style:UIAlertActionStyleDestructive
                                                           handler:^(UIAlertAction * _Nonnull action) {
                                                               [self.imageContainer removeImageAtIndex:index];
                                                           }]];
    
    [self.alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"Cancel", nil)
                                                             style:UIAlertActionStyleCancel
                                                           handler:nil]];
    
    
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.alertController.modalPresentationStyle = UIModalPresentationPopover;
        self.alertController.popoverPresentationController.sourceView = self.contentView;
        self.alertController.popoverPresentationController.sourceRect = [self.imageContainer sourceRectForImageIndex:index sourceView:self.contentView];
    }
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.formViewController presentViewController:self.alertController animated: true completion: nil];
    });

}

@end

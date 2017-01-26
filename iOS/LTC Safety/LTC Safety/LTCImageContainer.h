//
//  LTCImageContainer.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-14.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>

@class LTCImageContainer;
@protocol LTCImageContainerDelegate <NSObject>
@required
- (void)containerWillAddImage:(LTCImageContainer *)container;
- (void)container:(LTCImageContainer *)container willRemoveImageAtIndex:(NSInteger)index;
- (void)container:(LTCImageContainer *)container heightDidChange:(CGFloat)height;
@end

@interface LTCImageContainer : UIView
@property (nonatomic, weak) id <LTCImageContainerDelegate>delegate;
@property (readonly, nonatomic, strong) NSArray <UIImage *> *images;
- (CGRect)sourceRectForImageIndex:(NSUInteger)index sourceView:(UIView *)sourceView;
- (void)addImage:(UIImage *)image;
- (void)removeImageAtIndex:(NSUInteger)index;
@end
